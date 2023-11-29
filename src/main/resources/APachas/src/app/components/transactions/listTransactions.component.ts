import {AfterViewChecked, Component, OnInit} from '@angular/core';
import {MUserUserEvent} from "../../models/MUserUserEvent";
import {ProductService} from "../../services/product.service";
import {UserEventService} from "../../services/userEvent.service";
import {UserUserEventService} from "../../services/userUserEvent.service";
import {MEvent} from "../../models/MEvent";
import {MUser} from "../../models/MUser";
import {MUserEvent} from "../../models/MUserEvent";
import {DomSanitizer} from "@angular/platform-browser";
import {AuthenticationService} from "../../services/authentication.service";

@Component({
    selector: 'app-transactions',
    templateUrl: './listTransactions.component.html',
    styleUrls: ['./listTransactions.component.css']
})

export class ListTransactionsComponent implements OnInit {
    size: number= 6;
    transactionPage: number = 0;
    index: number;
    previous:string;
    next:string;

    defaultUserImage: string = "./assets/user16.jpg";
    event: MEvent;
    eventPartakers: MUser[] = [];
    totalPartaker: number=0;
    imagesPartakers: {[index:number]: any;} = {};

    authUserEvent: MUserEvent;

    transactionActorName = "";
    transactions: MUserUserEvent[] = [];
    totalTransactionPage:number= 0;
    selectedTransaction: MUserUserEvent = new MUserUserEvent();

    constructor(private productService: ProductService,
                private userEventService: UserEventService,
                private userUserEventService: UserUserEventService,
                private authenticationService: AuthenticationService,
                private sanitizer: DomSanitizer) {}

    ngOnInit() {
        if (localStorage.getItem("transactions") != undefined){
            this.event = JSON.parse(<string>localStorage.getItem("transactions"));
            this.getTransactions();
            this.getPartakers(this.event.eventId);
            this.getAuthUserEvent();
        }else{
            this.event = new MEvent();
        }
    }

    private getURLPartaker(partakers: MUser[]){
        partakers.forEach((partaker) => {
            this.imagesPartakers[partaker.userId] = this.sanitizer.bypassSecurityTrustUrl(partaker.userPhoto);
        });
    }

    getPartakers(eventId:number){
        this.userEventService.getPartakers(eventId).subscribe((response) => {
            this.eventPartakers.push(...response);
            this.getURLPartaker(response);
        });
    }

    getActor(actorId: number): MUser{
        let actor: MUser = null;
        this.eventPartakers.forEach((partaker) =>{
            if (partaker.userId == actorId){
                actor = partaker;
            }
        });
        return actor;
    }

    getTotalPartakers(eventId:number){
        this.userEventService.countPartakers(eventId).subscribe((number)=>{
            this.totalPartaker = number;
        });
    }

    getTransactions(){
        this.userUserEventService.getPageableUserUserEventsByEvent(this.event.eventId, this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.totalTransactionPages();

        });
    }
    paginationClass(){
        if(this.transactionPage!=0 && this.transactionPage+1<this.totalTransactionPage){
            this.previous = "col-xxl-9 col-xl-9 col-lg-9 col-md-9 col-sm-9 col-6";
            this.next = "col-xxl-3 col-xl-3 col-lg-3 col-md-3 col-sm-3 col-6";
        }else if (this.transactionPage==0 && this.transactionPage+1<this.transactionPage){
            this.previous = "";
            this.next = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
        }else if(this.transactionPage!=0 && this.transactionPage+1==this.transactionPage){
            this.previous = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
            this.next = "";
        }else{
            this.previous = "";
            this.next = "";
        }
    }

    getAuthUserEvent(){
        this.userEventService.getUserEvent(this.event.eventId, this.authenticationService.getUser().id).subscribe((userEvent) =>{
            this.authUserEvent = userEvent;
        });
    }

    colorDebt(senderId: number, receiverId: number): string{
        if (senderId == this.authUserEvent.userId){
            return "colorDebtSender";
        }else if (receiverId == this.authUserEvent.userId){
            return "colorDebtReceiver";
        }else{
            return "";
        }
    }




    private totalTransactionPages() {
        this.userUserEventService.countUserUserEventsByEvent(this.event.eventId).subscribe((response) => {
            this.totalTransactionPage = Math.ceil(response/this.size);
        });
    }

    setTransactionPage(page: number){
        this.transactionPage += page;
        this.transactionPagination();
        this.paginationClass();
    }

    private transactionPagination(){
        if(this.transactionActorName == ""){
            this.getTransactions();
        }else{
            this.searchTransaction();
        }
    }

    private searchTransaction(){
        this.userUserEventService.getPageableSearchUserUserEventsByEvent(this.transactionActorName, this.event.eventId, this.transactionPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.searchTransactionTotalPages();
        });
    }

    searchTransactionInput(){
        this.transactionPage=0;
        this.transactionPagination();
    }


    cost(cost: number): number{
        return Math.round(cost*100)/100;
    }

    private searchTransactionTotalPages(){
        this.userUserEventService.countSearchUserUserEventsByEvent(this.transactionActorName, this.event.eventId).subscribe((response) => {
            this.totalTransactionPage = Math.ceil(response/this.size);
        });
    }
}