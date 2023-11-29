import {Component, OnInit} from '@angular/core';
import {UserEventService} from "../../../services/userEvent.service";
import {DomSanitizer} from "@angular/platform-browser";
import {AuthenticationService} from "../../../services/authentication.service";
import {MProduct} from "../../../models/MProduct";
import {ProductService} from "../../../services/product.service";
import {MEvent} from "../../../models/MEvent";
import {MUser} from "../../../models/MUser";
import {UserProductService} from "../../../services/userProduct.service";
import {MUserEvent} from "../../../models/MUserEvent";
import {MUserUserEvent} from "../../../models/MUserUserEvent";
import {UserUserEventService} from "../../../services/userUserEvent.service";

export enum PRODUCTJOIN {
    JOIN = 'Participar', CANCEL = 'Quitar participación'
}

@Component({
    selector: 'app-products',
    templateUrl: './listProducts.component.html',
    styleUrls: ['./listProducts.component.css']
})

export class ListProductsComponent implements OnInit {
    size: number= 6;
    index: number;
    previous:string;
    next:string;
    pageDirection: number;

    //EVENTO
    defaultEventImage: string = "./assets/event7.jpg";
    defaultUserImage: string = "./assets/user16.jpg";
    event: MEvent;
    eventPartakers: MUser[] = [];
    eventPartakersStored: MUser[] = [];
    pagePartaker = 0;
    sizePartaker = 6;
    totalPartaker: number=0;
    imagesPartakers: {[index:number]: any;} = {};
    imageEvent: any;
    moneyColor: string;
    moneyText: string;
    previousMember:string;
    nextMember: string;


    //PRODUCTOS
    defaultImage: string = "./assets/product3_2.jpg";
    productName = "";
    products: MProduct[] = [];
    imagesProducts: {[index:number]: any;} = {};
    status: {[index: number]: any;} = {};
    totalProductPage:number= 0;
    productPage: number= 0;
    selectedProduct: MProduct = new MProduct();
    selectedStatus: string ="";

    //GASTOS
    userName: string;
    userEvents: MUserEvent[] = [];
    totalUserEventPage:number= 0;
    userEventPage: number= 0;
    authUserEvent: MUserEvent;
    totalExpense: number = 0;
    totalEventExpense: number = 0;
    totalProductCost: number = 0;
    debt: number = 0;

    //TRANSACCIÓN
    transactions: MUserUserEvent[] = [];
    transactionPage: number= 0;
    selectedTransaction: MUserUserEvent = new MUserUserEvent();

    constructor(private productService: ProductService,
                private userEventService: UserEventService,
                private userUserEventService: UserUserEventService,
                private userProductService: UserProductService,
                private authenticationService: AuthenticationService,
                private sanitizer: DomSanitizer) {}

    ngOnInit() {
        if (localStorage.getItem("products") != undefined){
            this.event = JSON.parse(<string>localStorage.getItem("products"));
            this.partakerReset();
            this.getProducts();
            this.getPartakers(this.event.eventId);
            this.getTotalPartakers(this.event.eventId);
            this.getUserEvents();
            this.getURLEvent();
            this.paginationClass();
            this.getAuthUserEvent();
            this.paginationMembersClass();
        }else{
            this.event = new MEvent();
        }
    }

    changeStyle($event){
        if ($event.type == "mouseover"){
            this.moneyColor = "moneyColor";
            this.moneyText = "moneyText";
        }else{
            this.moneyColor = "";
            this.moneyText= "";
        }
    }

    cost(cost: number): number{
        return Math.round(cost*100)/100;
    }

    private getURLEvent(){
        this.imageEvent= this.sanitizer.bypassSecurityTrustUrl(this.event.eventPhoto);
    }

    private getURLPartaker(partakers: MUser[]){
        partakers.forEach((partaker) => {
            this.imagesPartakers[partaker.userId] = this.sanitizer.bypassSecurityTrustUrl(partaker.userPhoto);
        });
    }

    getPartakers(eventId:number){
        this.userEventService.getPageablePartakers(eventId,this.pagePartaker, this.sizePartaker).subscribe((response) => {
            this.eventPartakers.push(...response);
            this.getURLPartaker(response);
            this.paginationMembersClass();
        });
    }

    getTotalPartakers(eventId:number){
        this.userEventService.countPartakers(eventId).subscribe((number)=>{
            this.totalPartaker = number;
        });
    }

    getMorePartakers(){
        this.pagePartaker +=1;
        if (this.eventPartakers.length < this.eventPartakersStored.length){
            this.eventPartakers = this.eventPartakersStored.slice(0,this.sizePartaker*(this.pagePartaker+1));
        }else{
            this.getPartakers(this.event.eventId);
        }
        this.paginationMembersClass();
    }

    getLessPartakers(){
        if (this.eventPartakersStored.length != this.totalPartaker){
            this.eventPartakersStored = this.eventPartakers;
            this.eventPartakers = this.eventPartakers.slice(0,this.sizePartaker*this.pagePartaker);
        }else{
            this.eventPartakers = this.eventPartakersStored.slice(0, this.sizePartaker*this.pagePartaker);
        }
        this.pagePartaker -=1;
        this.paginationMembersClass();
    }

    partakerReset(){
        this.eventPartakers = [];
        this.eventPartakersStored = [];
        this.pagePartaker = 0;
    }

    ownerLabel(userId:number):string{
        let value:string = "";
        if (userId == this.event.eventOwner){
            value = "Administrador";
        }
        return value;
    }

    getTotalExpense(){
        if (this.authUserEvent != undefined){
            this.totalExpense = this.authUserEvent.totalExpense;
        }
    }

    getDebt(){
        if (this.authUserEvent != undefined){
            this.debt = this.authUserEvent.debt;
        }
    }

    ownerBorder(userId: number):string{
        if(userId == this.event.eventOwner){
            return "owner";
        }
    }

    //PRODUCTOS

    getProducts(){
        this.productService.getPageableProducts(this.event.eventId, this.productPage, this.size).subscribe((response) => {
            this.products = response;
            this.getAuthUserEvent();
            this.setSelectedProductPage();
            this.getURLProduct(response);
            this.totalProductPages();
            this.getStatus(response);
            this.sumTotalEventExpense();
            this.sumTotalProductCost();
            this.getTotalExpense();
            this.getDebt();
        });
    }

    paginationClass(){
        if(this.productPage!=0 && this.productPage+1<this.totalProductPage){
            this.previous = "col-xxl-9 col-xl-9 col-lg-9 col-md-9 col-sm-9 col-6";
            this.next = "col-xxl-3 col-xl-3 col-lg-3 col-md-3 col-sm-3 col-6";
        }else if (this.productPage==0 && this.productPage+1<this.totalProductPage){
            this.previous = "";
            this.next = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
        }else if(this.productPage!=0 && this.productPage+1==this.totalProductPage){
            this.previous = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
            this.next = "";
        }else{
            this.previous = "";
            this.next = "";
        }
    }

    private paginationMembersClass(){
        if(this.eventPartakers.length > this.sizePartaker && this.eventPartakers.length < this.totalPartaker){
            this.previousMember = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6";
            this.nextMember = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6 nextMember";
        }else if (this.eventPartakers.length <= this.sizePartaker && this.eventPartakers.length < this.totalPartaker){
            this.previousMember = "";
            this.nextMember = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
        }else if(this.eventPartakers.length > this.sizePartaker && this.eventPartakers.length >= this.totalPartaker){
            this.previousMember = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
            this.nextMember = "";
        }else{
            this.previousMember = "";
            this.nextMember = "";
        }
    }

    private getURLProduct(products: MProduct[]){
        products.forEach((product) => {
            this.imagesProducts[product.productId] = this.sanitizer.bypassSecurityTrustUrl(product.productPhoto);
         });
    }

    setProduct(){
        this.selectedProduct = new MProduct();
    }

    setSelectedProductPage(){
        if (this.pageDirection != undefined){
            if (this.pageDirection == -1){
                this.index = this.size-1;
            }else if (this.pageDirection == 1){
                this.index = 0;
            }
            this.selectedProduct = this.products[this.index];
            this.selectedStatus = this.status[this.index];
        }
    }

    private totalProductPages() {
        this.productService.countProducts(this.event.eventId).subscribe((response) => {
            this.totalProductPage = Math.ceil(response/this.size);
        });
    }

    selectProduct(index:number){
        this.selectedProduct = this.products[index];
        this.selectedStatus = this.status[this.selectedProduct.productId];
        this.index = index;
    }

    setSelectedProduct(product: number) {
        this.pageDirection = product.valueOf();
        if ( this.products[this.index + product.valueOf()] != undefined){
            this.selectProduct(this.index + product.valueOf());
        }else{
            this.setProductPage(product.valueOf());

        }
    }

    setProductPage(page: number){
        this.productPage += page;
        this.productPagination();
        this.paginationClass();
    }

    private productPagination(){
        if(this.productName == ""){
            this.getProducts();
        }else{
            this.searchProduct();
        }
    }

    getProductNext (): boolean{
        if (this.productPage != this.totalProductPage-1 || this.products[this.index+1] != undefined){
            return true;
        }else{
            return false;
        }
    }

    getProductPrevious():boolean{
        if (this.productPage != 0 || this.products[this.index-1] != undefined){
            return true;
        }else{
            return false;
        }
    }

    private searchProduct(){
        this.productService.getPageableSearchProducts(this.productName, this.event.eventId, this.productPage, this.size).subscribe((response) => {
            this.products = response;
            this.setSelectedProductPage();
            this.searchProductTotalPages();
            this.getURLProduct(response);
            this.getStatus(response);

        });
    }

    searchProductInput(){
        this.productPage=0;
        this.productPagination();
    }

    private searchProductTotalPages(){
        this.productService.countSearchProducts(this.productName, this.event.eventId).subscribe((response) => {
            this.totalProductPage = Math.ceil(response/this.size);
        });
    }

    private getStatus (mProducts: MProduct[]) {
        mProducts.forEach((mProduct) => {
            this.userProductService.getUserProduct(mProduct.productId, this.authenticationService.getUser().id).subscribe((response) => {
                if (response.productId == 0 || response.userProductActive == false){
                    this.status[mProduct.productId] = PRODUCTJOIN.JOIN;
                }else if (response.userProductActive == true){
                    this.status[mProduct.productId] = PRODUCTJOIN.CANCEL;
                }
            });
        });
    }

    messageStatus(productId:number): string{
        if (this.status[productId] == PRODUCTJOIN.JOIN.valueOf()){
            return "Solicitar participación";
        }else{
            return "Participando";
        }
    }

    //GASTOS
    getUserEvents(){
        this.userEventService.getPageableUserEvents(this.event.eventId, this.userEventPage, this.totalPartaker).subscribe((response) => {
            this.userEvents = response;
            this.totalUserEventPages();

        });
    }

    getUserExpense(userId: number): number{
        let expense: number = 0;
            this.userEvents.forEach((userEvent) =>{
           if (userEvent.userId == userId){
               expense = userEvent.totalExpense;
           }
        });
        return expense;
    }

    showExpenseButton(userId: number): boolean{
        if (userId == this.authenticationService.getUser().id && this.event.eventOpen){
            return true;
        }else{
            return false;
        }
    }

    getAuthUserEvent(){
        this.userEventService.getUserEvent(this.event.eventId, this.authenticationService.getUser().id).subscribe((userEvent) =>{
            this.authUserEvent = userEvent;
            this.debt = this.authUserEvent.debt;
            this.totalExpense = this.authUserEvent.totalExpense;
            this.sumTotalEventExpense();
            this.sumTotalProductCost();
        });
    }

    sumTotalEventExpense(){
        this.userEventService.sumTotalEventExpense(this.event.eventId).subscribe((totalEventExpense)=>{
            this.totalEventExpense = totalEventExpense;
        });
    }

    sumTotalProductCost(){
        this.productService.sumTotalProductCost(this.event.eventId).subscribe((totalProductCost) => {
            this.totalProductCost = totalProductCost;
        });
    }

    private totalUserEventPages() {
        this.userEventService.countUserEvents(this.event.eventId).subscribe((response) => {
            this.totalUserEventPage = Math.ceil(response/this.size);
        });
    }

    setUserEventPage(page: number){
        this.userEventPage += page;
        this.getUserEvents();
        this.paginationClass();
    }

    private userEventPagination(){
        if(this.userName == ""){
            this.getUserEvents();
        }else{
            this.searchUserEvent();
        }
    }

    private searchUserEvent(){
        this.userEventService.getPageableSearchUserEvents(this.userName, this.event.eventId, this.userEventPage, this.size).subscribe((response) => {
            this.userEvents = response;
            this.searchUserEventTotalPages();
        });
    }

    searchUserEventInput(){
        this.userEventPage=0;
        this.userEventPagination();
    }

    private searchUserEventTotalPages(){
        this.userEventService.countSearchUserEvents(this.userName, this.event.eventId).subscribe((response) => {
            this.totalUserEventPage = Math.ceil(response/this.size);
        });
    }

   /* //TRANSACCIONES

    getTransactions(){
        this.userUserEventService.getPageableUserUserEvents(this.event.eventId, this.productPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.setSelectedTransactionPage();
            this.totalTransactionPages();

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

    colorDebt(senderId: number): string{
        if (senderId == this.authUserEvent.userId){
            return "colorDebt";
        }else{
            return "";
        }
    }

    colorButtonDebt(senderId: number): string{
        if (senderId == this.authUserEvent.userId){
            return "colorButtonDebt";
        }else{
            return "";
        }
    }

    setSelectedTransactionPage(){
        if (this.pageDirection != undefined){
            if (this.pageDirection == -1){
                this.index = this.size-1;
            }else if (this.pageDirection == 1){
                this.index = 0;
            }
            this.selectedTransaction = this.transactions[this.index];
        }
    }

    private totalTransactionPages() {
        this.userUserEventService.countUserUserEvents(this.event.eventId).subscribe((response) => {
            this.totalTransactionPage = Math.ceil(response/this.size);
        });
    }

    selectTransaction(index:number){
        this.selectedTransaction = this.transactions[index];
        this.index = index;
    }

    setSelectedTransaction(transaction: number) {
        this.pageDirection = transaction.valueOf();
        if ( this.transactions[this.index + transaction.valueOf()] != undefined){
            this.selectTransaction(this.index + transaction.valueOf());
        }else{
            this.setTransactionPage(transaction.valueOf());

        }
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

    getTransactionNext (): boolean{
        if (this.transactionPage != this.totalTransactionPage-1 || this.transactions[this.index+1] != undefined){
            return true;
        }else{
            return false;
        }
    }

    getTransactionPrevious():boolean{
        if (this.transactionPage != 0 || this.transactions[this.index-1] != undefined){
            return true;
        }else{
            return false;
        }
    }

    private searchTransaction(){
        this.userUserEventService.getPageableSearchUserUserEvents(this.transactionActorName, this.event.eventId, this.productPage, this.size).subscribe((response) => {
            this.transactions = response;
            this.setSelectedTransactionPage();
            this.searchTransactionTotalPages();
        });
    }

    searchTransactionInput(){
        this.transactionPage=0;
        this.transactionPagination();
    }

    private searchTransactionTotalPages(){
        this.userUserEventService.countSearchUserUserEvents(this.transactionActorName, this.event.eventId).subscribe((response) => {
            this.totalTransactionPage = Math.ceil(response/this.size);
        });
    }*/
}