import {Component, OnInit} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";
import {AuthenticationService} from "../../../services/authentication.service";
import {MEvent} from "../../../models/MEvent";
import {UserEventService} from "../../../services/userEvent.service";
import {STATUS} from "../../users/listUsers/listUsers.component";

@Component({
    selector: 'app-events',
    templateUrl: './listEvents.component.html',
    styleUrls: ['./listEvents.component.css']
})
export class ListEventsComponent implements OnInit {
    eventName = "";
    events: MEvent[] = [];
    images: {[index:number]: any;} = {};
    status: {[index: number]: any;} = {};
    defaultImage: string = "./assets/event7.jpg";
    totalPage:number= 0;
    page: number= 0;
    selectedEvent: MEvent = new MEvent();
    selectedStatus: string ="";
    size: number= 6;
    index: number;
    previous:string;
    next:string;
    pageDirection: number;
    checked: boolean;

    constructor(private userEventService: UserEventService,
                private authenticationService: AuthenticationService,
                private sanitizer: DomSanitizer) {}

    ngOnInit() {
        this.getEvents();
        this.paginationClass();
        this.checked = false;
    }

    toggleCheck(){
        this.page = 0;
        this.checked = !this.checked;
        this.pagination();
    }

    checkFilter():string{
        if (this.checked){
            return "checkFilter"
        }else{
            return "";
        }
    }

    setEvent(){
        this.selectedEvent = new MEvent();
    }

    selectEvent(index:number){
        this.selectedEvent = this.events[index];
        this.selectedStatus = this.status[this.selectedEvent.eventId];
        this.index = index;
    }

    setSelectedEventPage(){
        if (this.pageDirection != undefined){
            if (this.pageDirection == -1){
                this.index = this.size-1;
            }else if (this.pageDirection == 1){
                this.index = 0;
            }
            this.selectedEvent = this.events[this.index];
            this.selectedStatus = this.status[this.index];
        }
    }

    getEvents(){
        this.userEventService.getPageableEvents(this.authenticationService.getUser().id, this.page, this.size).subscribe((response) => {
            this.events = response;
            this.setSelectedEventPage();
            this.getStatus(response);
            this.getURL(response);
            this.totalPages();
        });
    }

    getEventsWithFinished(){
        this.userEventService.getPageableEventsWithFinished(this.authenticationService.getUser().id, this.page, this.size).subscribe((response) => {
            this.events = response;
            this.setSelectedEventPage();
            this.getStatus(response);
            this.getURL(response);
            this.totalPagesWithFinished();
        });
    }

    paginationClass(){
        if(this.page!=0 && this.page+1<this.totalPage){
            this.previous = "col-xxl-9 col-xl-9 col-lg-9 col-md-9 col-sm-9 col-9";
            this.next = "col-xxl-3 col-xl-3 col-lg-3 col-md-3 col-sm-3 col-3";
        }else if (this.page==0 && this.page+1<this.totalPage){
            this.previous = "";
            this.next = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 cpl-12";
        }else if(this.page!=0 && this.page+1==this.totalPage){
            this.previous = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
            this.next = "";
        }else{
            this.previous = "";
            this.next = "";
        }
    }

    setPage(page: number){
        this.page += page;
        this.pagination();
        this.paginationClass();
    }

    private pagination(){
        if (this.checked){
            if(this.eventName == ""){
                this.getEventsWithFinished();
            }else{
                this.searchWithFinished();
            }
        }else{
            if(this.eventName == ""){
                this.getEvents();
            }else{
                this.search();
            }
        }
    }

    private getURL(events: MEvent[]){
        events.forEach((event) => {
            this.images[event.eventId] = this.sanitizer.bypassSecurityTrustUrl(event.eventPhoto);
        });
    }

    private search(){
        this.userEventService.getPageableSearchEvents(this.eventName, this.authenticationService.getUser().id, this.page, this.size).subscribe((response) => {
            this.events = response;
            this.setSelectedEventPage();
            this.getStatus(response);
            this.searchTotalPages();
            this.getURL(response);
        });
    }

    private searchWithFinished(){
        this.userEventService.getPageableSearchEventsWithFinished(this.eventName, this.authenticationService.getUser().id, this.page, this.size).subscribe((response) => {
            this.events = response;
            this.setSelectedEventPage();
            this.getStatus(response);
            this.searchTotalPagesWithFinished();
            this.getURL(response);
        });
    }

    searchInput(){
        this.page=0;
        this.pagination();
    }

    private totalPages() {
        this.userEventService.countEvents(this.authenticationService.getUser().id).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }

    private totalPagesWithFinished(){
        this.userEventService.countEventsWithFinished(this.authenticationService.getUser().id).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }

    private searchTotalPages(){
        this.userEventService.countSearchEvents(this.eventName, this.authenticationService.getUser().id).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }

    private searchTotalPagesWithFinished(){
        this.userEventService.countSearchEventsWithFinished(this.eventName, this.authenticationService.getUser().id).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }

    getNext (): boolean{
        if (this.page != this.totalPage-1 || this.events[this.index+1] != undefined){
            return true;
        }else{
            return false;
        }
    }

    getPrevious():boolean{
        if (this.page != 0 || this.events[this.index-1] != undefined){
            return true;
        }else{
            return false;
        }
    }

    setSelectedEvent(event: number) {
        this.pageDirection = event.valueOf();
        if ( this.events[this.index + event.valueOf()] != undefined){
            this.selectEvent(this.index + event.valueOf());
        }else{
            this.setPage(event.valueOf());

        }
    }

    private statusValue (statusBD: boolean): string {
        let status: string;
        if (statusBD) {
            status = STATUS.FOLLOW;
        }else if (!statusBD){
            status = STATUS.PENDING;
        }else{
            status = STATUS.REQUEST;
        }
        return status;
    }

    private getStatus (mEvents: MEvent[]) {
        mEvents.forEach((mEvent) => {
            this.userEventService.getUserEvent(mEvent.eventId, this.authenticationService.getUser().id).subscribe((response) => {
                this.status[mEvent.eventId] = this.statusValue(response.accept);
            });
        });
    }
}