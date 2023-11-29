import {Component, OnInit} from "@angular/core";
import {MUser} from "../../../models/MUser";
import {UserUserEventService} from "../../../services/userUserEvent.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {DomSanitizer} from "@angular/platform-browser";
import {MUserUserEvent} from "../../../models/MUserUserEvent";
import {EventService} from "../../../services/event.service";
import {MEvent} from "../../../models/MEvent";
import {UserService} from "../../../services/user.service";

@Component({
    selector: 'app-transactionHistory',
    templateUrl: './transactionHistory.component.html',
    styleUrls: ['./transactionHistory.component.css']
})

export class TransactionHistoryComponent implements OnInit {
    size: number= 6;
    transactionPage: number = 0;
    index: number;
    previous:string;
    next:string;
    transactions: MUserUserEvent[] = [];
    defaultUserImage: string = "./assets/user16.jpg";
    defaultEventImage: string = "./assets/event7.jpg";
    imagesPartakers: {[index:number]: any;} = {};
    imagesEvents: {[index:number]: any;} = {};
    searchTransactionValue = "";
    totalTransactionPage:number= 0;
    transactionPartakers: MUser[] = [];
    transactionEvents: MEvent[] = [];
    checked: boolean[] = [false,false,false,false, false];
    message: string = "";
    selectedTransaction: MUserUserEvent;
    paid:boolean = false;
    confirmed: boolean = false;

    constructor(private userUserEventService: UserUserEventService,
                private userService: UserService,
                private eventService: EventService,
                private authenticationService: AuthenticationService,
                private sanitizer: DomSanitizer) {}

    ngOnInit() {
        this.getTransactions();
    }
    cost(cost: number): number{
        return Math.round(cost*100)/100;
    }

    onRequest($event){
        if ($event.valueOf() && this.paid){
            this.updatePaid();
        }else if ($event.valueOf() && this.confirmed){
            this.updateConfirmed();
        }
    }

    updatePaid(){
        this.userUserEventService.editPaid(this.selectedTransaction, true).subscribe(()=>{
            this.getTransactions();
            this.selectedTransaction = null;
            this.paid = false;
        });
    }

    updateConfirmed(){
        this.userUserEventService.editConfirmed(this.selectedTransaction, true).subscribe(()=>{
            this.getTransactions();
            this.selectedTransaction = null;
            this.confirmed = false;
        });
    }

    messageValue(transaction: MUserUserEvent){
        this.selectedTransaction = transaction;
        if (transaction.senderId == this.authenticationService.getUser().id){
            this.message = "¿Estás seguro de que quieres confirmar que has pagado?"
            this.paid = true;
        }else if (transaction.receiverId == this.authenticationService.getUser().id){
            this.message = "¿Estás seguro de que quieres confirmar que has recibido el pago?"
            this.confirmed = true;
        }else{
            this.message = "";
        }
    }

    toggleCheck(index: number){
        if (index != -1){

            if ((index == 0 && !this.checked[4]) || (index == 4 && !this.checked[0]) || (index == 1 && !this.checked[2]) || (index == 2 && !this.checked[1]) || index == 3) {
                this.searchTransactionValue = "";
                this.transactionPage = 0;
                this.checked[index] = !this.checked[index];
            }
        }
            if (this.checked[0] && !this.checked[1] && !this.checked[2] && !this.checked[3] && !this.checked[4]) { // 0 0 0 0 1
                this.getByEvent();
            } else if (!this.checked[0] && this.checked[1] && !this.checked[2] && !this.checked[3] && !this.checked[4]) { // 0 0 0 1 0
                this.getDebts();
            } else if (this.checked[0] && this.checked[1] && !this.checked[2] && !this.checked[3] && !this.checked[4]) { // 0 0 0 1 1
                this.getDebtsByEvent();
            } else if (!this.checked[0] && !this.checked[1] && this.checked[2] && !this.checked[3] && !this.checked[4]) { // 0 0 1 0 0
                this.getPayments();
            } else if (this.checked[0] && !this.checked[1] && this.checked[2] && !this.checked[3] && !this.checked[4]) { // 0 0 1 0 1
                this.getPaymentsByEvent();
            } else if (!this.checked[0] && !this.checked[1] && !this.checked[2] && this.checked[3] && !this.checked[4]) { // 0 1 0 0 0
                this.getFinished();
            } else if (this.checked[0] && !this.checked[1] && !this.checked[2] && this.checked[3] && !this.checked[4]) { // 0 1 0 0 1
                this.getFinishedByEvent();
            } else if (!this.checked[0] && this.checked[1] && !this.checked[2] && this.checked[3] && !this.checked[4]) { // 0 1 0 1 0
                this.getFinishedDebts();
            } else if (this.checked[0] && this.checked[1] && !this.checked[2] && this.checked[3] && !this.checked[4]) { // 0 1 0 1 1
                this.getFinishedDebtsByEvent();
            } else if (!this.checked[0] && !this.checked[1] && this.checked[2] && this.checked[3] && !this.checked[4]) { // 0 1 1 0 0
                this.getFinishedPayments();
            } else if (this.checked[0] && !this.checked[1] && this.checked[2] && this.checked[3] && !this.checked[4]) { // 0 1 1 0 1
                this.getFinishedPaymentsByEvent();
            } else if (!this.checked[0] && !this.checked[1] && !this.checked[2] && !this.checked[3] && this.checked[4]) { // 1 0 0 0 0
                this.getByDate();
            } else if (!this.checked[0] && this.checked[1] && !this.checked[2] && !this.checked[3] && this.checked[4]) { // 1 0 0 1 0
                this.getDebtsByDate();
            } else if (!this.checked[0] && !this.checked[1] && this.checked[2] && !this.checked[3] && this.checked[4]) { // 1 0 1 0 0
                this.getPaymentsByDate();
            } else if (!this.checked[0] && !this.checked[1] && !this.checked[2] && this.checked[3] && this.checked[4]) { // 1 1 0 0 0
                this.getFinishedByDate();
            } else if (!this.checked[0] && this.checked[1] && !this.checked[2] && this.checked[3] && this.checked[4]) { // 1 1 0 1 0
                this.getFinishedDebtsByDate();
            } else if (!this.checked[0] && !this.checked[1] && this.checked[2] && this.checked[3] && this.checked[4]) { // 1 1 1 0 0
                this.getFinishedPaymentsByDate();
            } else {
                this.getTransactions();
            }
    }

    getByEvent(){
        this.userUserEventService.getPageableUserUserEventsByAuthUserByEvent(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalTransactionPages();
            this.getPartakers();
            this.getEvents();
        });
    }

    getDebts(){
        this.userUserEventService.getPageableUserUserEventsDebtsByAuthUser(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalTransactionDebtsPages();
            this.getPartakers();
            this.getEvents();
        });
    }
    totalTransactionDebtsPages(){
        this.userUserEventService.countUserUserEventsDebtsByAuthUser(this.authenticationService.getUser().id).subscribe((response) => {
            this.totalTransactionPage = Math.ceil(response/this.size);
        });
    }

    getDebtsByEvent(){
        this.userUserEventService.getPageableUserUserEventsDebtsByAuthUserByEvent(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalTransactionDebtsPages();
            this.getPartakers();
            this.getEvents();
        });
    }

    getPayments(){
        this.userUserEventService.getPageableUserUserEventsPaymentsByAuthUser(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalTransactionPaymentsPages();
            this.getPartakers();
            this.getEvents();
        });
    }

    totalTransactionPaymentsPages(){
        this.userUserEventService.countUserUserEventsPaymentsByAuthUser(this.authenticationService.getUser().id).subscribe((response) => {
            this.totalTransactionPage = Math.ceil(response/this.size);
        });
    }

    getPaymentsByEvent(){
        this.userUserEventService.getPageableUserUserEventsPaymentsByAuthUserByEvent(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalTransactionPaymentsPages();
            this.getPartakers();
            this.getEvents();
        });
    }

    getFinished() {
        this.userUserEventService.getPageableNotFinishedUserUserEventsByAuthUser(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalFinishedTransactionPages();
            this.getPartakers();
            this.getEvents();
        });
    }

    totalFinishedTransactionPages(){
        this.userUserEventService.countFinishedUserUserEventsByAuthUser(this.authenticationService.getUser().id).subscribe((response) => {
            this.totalTransactionPage = Math.ceil(response/this.size);
        });
    }

    getFinishedByEvent(){
        this.userUserEventService.getPageableNotFinishedUserUserEventsByAuthUserByEvent(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalFinishedTransactionPages();
            this.getPartakers();
            this.getEvents();
        });
    }

    getFinishedDebts(){
        this.userUserEventService.getPageableNotFinishedUserUserEventsDebtsByAuthUser(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalFinishedTransactionDebtsPages();
            this.getPartakers();
            this.getEvents();
        });
    }

    totalFinishedTransactionDebtsPages(){
        this.userUserEventService.countFinishedUserUserEventsDebtsByAuthUser(this.authenticationService.getUser().id).subscribe((response) => {
            this.totalTransactionPage = Math.ceil(response/this.size);
        });
    }

    getFinishedDebtsByEvent(){
        this.userUserEventService.getPageableNotFinishedUserUserEventsDebtsByAuthUserByEvent(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalFinishedTransactionDebtsPages();
            this.getPartakers();
            this.getEvents();
        });
    }

    getFinishedPayments(){
        this.userUserEventService.getPageableNotFinishedUserUserEventsPaymentsByAuthUser(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalFinishedTransactionPaymentsPages();
            this.getPartakers();
            this.getEvents();
        });
    }

    totalFinishedTransactionPaymentsPages(){
        this.userUserEventService.countFinishedUserUserEventsPaymentsByAuthUser(this.authenticationService.getUser().id).subscribe((response) => {
            this.totalTransactionPage = Math.ceil(response/this.size);
        });
    }

    getFinishedPaymentsByEvent(){
        this.userUserEventService.getPageableNotFinishedUserUserEventsPaymentsByAuthUserByEvent(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalFinishedTransactionPaymentsPages();
            this.getPartakers();
            this.getEvents();
        });
    }

    getByDate(){
        this.userUserEventService.getPageableUserUserEventsByAuthUserByDate(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalTransactionPages();
            this.getPartakers();
            this.getEvents();
        });
    }


    getDebtsByDate(){
        this.userUserEventService.getPageableUserUserEventsDebtsByAuthUserByDate(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalTransactionDebtsPages();
            this.getPartakers();
            this.getEvents();
        });
    }

    getPaymentsByDate(){
        this.userUserEventService.getPageableUserUserEventsPaymentsByAuthUserByDate(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalTransactionPaymentsPages();
            this.getPartakers();
            this.getEvents();
        });
    }

    getFinishedByDate(){
        this.userUserEventService.getPageableNotFinishedUserUserEventsByAuthUserByDate(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalFinishedTransactionPages();
            this.getPartakers();
            this.getEvents();
        });
    }

    getFinishedDebtsByDate(){
        this.userUserEventService.getPageableNotFinishedUserUserEventsDebtsByAuthUserByDate(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalFinishedTransactionDebtsPages();
            this.getPartakers();
            this.getEvents();
        });
    }

    getFinishedPaymentsByDate(){
        this.userUserEventService.getPageableNotFinishedUserUserEventsPaymentsByAuthUserByDate(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalFinishedTransactionPaymentsPages();
            this.getPartakers();
            this.getEvents();
        });
    }

    checkFilter(index: number):string{
        if (this.checked[index]){
            return "checkFilter"
        }else{
            return "";
        }
    }

    checkedFilter(index: number): string{
        if (index == 0){
            return "Ordenar por evento";
        }else if (index == 1){
            return "Deudas";
        }else if (index == 2){
            return "Pagos";
        }else if (index == 3){
            return "Pendientes";
        }else if (index == 4){
            return "Ordenar por más reciente";
        }

    }

    costColor(transaction: MUserUserEvent): string {
        if (this.authenticationService.getUser().id == transaction.senderId){
            return "costColor";
        }else{
            return "";
        }
    }

    getStatus(transaction: MUserUserEvent): string{
        let message: string = "";
        if (this.authenticationService.getUser().id == transaction.senderId){
            if (transaction.paid){
                if (transaction.confirmed){
                    message = "Pagado";
                }else{
                    message = "Pendiente de confirmación";
                }
            }else{
                message = "Pagar";
            }
        }else{
            if (transaction.paid){
                if (transaction.confirmed){
                    message = "Recibido";
                }else{
                    message = "Confirmar";
                }
            }else{
                message = "Pendiente de cobro";
            }
        }
        return message;
    }

    getTransactions(){
        this.userUserEventService.getPageableUserUserEventsByAuthUser(this.authenticationService.getUser().id,this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalTransactionPages();
            this.getPartakers();
            this.getEvents();

        });
    }

    private totalTransactionPages() {
        this.userUserEventService.countUserUserEventsByAuthUser(this.authenticationService.getUser().id).subscribe((response) => {
            this.totalTransactionPage = Math.ceil(response/this.size);
        });
    }

    getPartakers(){
        this.transactions.forEach((transaction)=>{
            if (this.transactionPartakers.find(partaker => partaker.userId === transaction.senderId) == undefined){
                this.getUser(transaction.senderId);
            }
            if (this.transactionPartakers.find(partaker => partaker.userId === transaction.receiverId) == undefined){
                this.getUser(transaction.receiverId);
            }
        });
    }

    private getUser(id: number) {
        this.userService.getUserById(id).subscribe((response) => {
            this.transactionPartakers.push(response);
            this.getURLPartaker(response);
        });
    }

    private getURLPartaker(partaker: MUser){
        this.imagesPartakers[partaker.userId] = this.sanitizer.bypassSecurityTrustUrl(partaker.userPhoto);
    }

    getEvents(){
        this.transactions.forEach((transaction)=>{
            if (this.transactionEvents.find(event => event.eventId === transaction.eventId) == undefined){
                this.getEvent(transaction.eventId);
            }
        });
    }

    private getEvent(id: number){
        this.eventService.getEvent(id).subscribe((response) => {
            this.transactionEvents.push(response);
            this.getURLEvent(response);
        });
    }

    private getURLEvent(event: MEvent){
        if (event.eventPhoto != null){
            this.imagesEvents[event.eventId] = this.sanitizer.bypassSecurityTrustUrl(event.eventPhoto);
        }
    }

    getActor(actorId: number): MUser{
        let actor: MUser = new MUser();
        this.transactionPartakers.forEach((partaker) =>{
            if (partaker.userId == actorId){
                actor = partaker;
            }
        });
        return actor;
    }

    getEventTransaction(eventId: number): MEvent{
        let eventTransaction: MEvent = new MEvent();
        this.transactionEvents.forEach((event) =>{
            if (event.eventId == eventId){
                eventTransaction = event;
            }
        });
        return eventTransaction;
    }

    paginationClass(){
        if(this.transactionPage!=0 && this.transactionPage+1<this.totalTransactionPage){
            this.previous = "col-xxl-9 col-xl-9 col-lg-9 col-md-9 col-sm-9";
            this.next = "col-xxl-3 col-xl-3 col-lg-3 col-md-3 col-sm-3";
        }else if (this.transactionPage==0 && this.transactionPage+1<this.transactionPage){
            this.previous = "";
            this.next = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12";
        }else if(this.transactionPage!=0 && this.transactionPage+1==this.transactionPage){
            this.previous = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12";
            this.next = "";
        }else{
            this.previous = "";
            this.next = "";
        }
    }

    setTransactionPage(page: number){
        this.transactionPage += page;
        this.transactionPagination();
        this.paginationClass();
    }

    private transactionPagination(){
        if(this.searchTransactionValue == ""){
            this.toggleCheck(-1);
        }else{
            this.searchTransaction();
        }
    }

    private searchTransaction(){
        this.userUserEventService.getPageableSearchUserUserEventsByAuthUser(this.searchTransactionValue, this.authenticationService.getUser().id, this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.searchTransactionTotalPages();
        });
    }

    searchTransactionInput(){
        this.transactionPage=0;
        this.transactionPagination();
    }

    private searchTransactionTotalPages(){
        this.userUserEventService.countSearchUserUserEventsByAuthUser(this.searchTransactionValue, this.authenticationService.getUser().id).subscribe((response) => {
            this.totalTransactionPage = Math.ceil(response/this.size);
        });
    }
}