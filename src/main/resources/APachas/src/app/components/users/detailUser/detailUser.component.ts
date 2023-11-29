import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {AuthenticationService} from "../../../services/authentication.service";
import {STATUS} from "../listUsers/listUsers.component";
import {UserUserService} from "../../../services/userUser.service";
import {MUser} from "../../../models/MUser";
import {MGroup} from "../../../models/MGroup";
import {MUserUser} from "../../../models/MUserUser";
import {MEvent} from "../../../models/MEvent";
import {GroupUserService} from "../../../services/groupUser.service";
import {UserEventService} from "../../../services/userEvent.service";

export enum MESSAGE{
    CANCELREQUEST = '¿Cancelar solicitud?', UNFOLLOW = '¿Dejar de seguir?', ALLOWREQUEST = '¿Aceptar solicitud?', SENTREQUEST = '¿Enviar solicitud?'
}

@Component({
    selector: 'app-detailUser',
    templateUrl: './detailUser.component.html',
    styleUrls: ['./detailUser.component.css']
})

export class DetailUserComponent implements OnInit {

    @Output()
    eventMessage = new EventEmitter<number>();
    defaultEventImage: string = "./assets/event7.jpg";
    defaultGroupImage: string = "./assets/group7_2.jpg";
    defaultImage: string = "./assets/user16.jpg";
    previousUser: string;
    nextUser: string;

    previousFriendClass: string;
    nextFriendClass: string;

    previousGroupClass: string;
    nextGroupClass: string;

    previousEventClass: string;
    nextEventClass: string;

    mutualGroups: MGroup[] = [];
    mutualGroupsStored: MGroup[] = [];
    pageMutualGroups = 0;
    sizeMutualGroups = 6;
    totalMutualGroups: number=0;

    mutualFriends: MUser[] = [];
    mutualFriendsStored: MUser[] = [];
    pageMutualFriends = 0;
    sizeMutualFriends = 4;
    totalMutualFriends: number=0;

    mutualEvents: MEvent[] = [];
    mutualEventsStored: MEvent[] = [];
    pageMutualEvents = 0;
    sizeMutualEvents = 4;
    totalMutualEvents: number=0;

    message:string="";
    _status:string="";
    _previous: boolean = false;
    _next: boolean = false;
    _user: MUser = new MUser();

    @Output()
    eventDetail = new EventEmitter<number>();

    constructor(private authenticationService: AuthenticationService,
                private userUserService: UserUserService,
                private userEventService: UserEventService,
                private groupUserService: GroupUserService
    ) {
    }

    ngOnInit() {
        this.paginationUserClass();
        this.paginationFriendClass();
        this.paginationEventClass();

    }

    get user(){
        this.paginationFriendClass();
        this.paginationGroupClass();
        this.paginationEventClass();

        return this._user;
    }

    @Input() set user (user: MUser) {
        if (user != undefined) {
            this._user = user;
            this.mutualReset();
            if (this.user.userId != null){
                this.getMutualUserGroups(this.user.userId);
                this.getMutualFriends(this.user.userId);
                this.getMutualEvents(this.user.userId);

                this.getTotalMutualFriends(this.user.userId);
                this.getTotalMutualGroups(this.user.userId);
                this.getTotalMutualEvents(this.user.userId);


            }else{
                this._user = new MUser();
            }
        }
        this.paginationUserClass();
        this.paginationFriendClass();
        this.paginationGroupClass();
        this.paginationEventClass();

    }


    mutualReset(){
        this.mutualGroups = [];
        this.mutualFriends = [];
        this.mutualEvents = [];

        this.mutualGroupsStored = [];
        this.mutualFriendsStored = [];
        this.mutualEventsStored = [];

        this.pageMutualGroups = 0;
        this.pageMutualFriends = 0;
        this.pageMutualEvents = 0;
    }


    get previous(){
        return this._previous;
    }

    @Input() set previous( previous: boolean){
        this._previous = previous;
        this.paginationUserClass();
    }

    get status(){
        return this._status;
    }

    @Input() set status( status: string){
        this._status = status;
        this.setMessage();
    }

    get next(){
        return this._next;
    }

    @Input() set next( next: boolean){
        this._next = next;
        this.paginationUserClass();

    }

    setPage(number: number){
        this.eventDetail.emit(number);
    }

    setMessage(){
        if (this.status == STATUS.FOLLOW){
            this.message = MESSAGE.UNFOLLOW;
        }else if (this.status == STATUS.SENT){
            this.message = MESSAGE.CANCELREQUEST;
        }else if (this.status == STATUS.PENDING){
            this.message = MESSAGE.ALLOWREQUEST;
        }else if (this.status == STATUS.REQUEST){
            this.message = MESSAGE.SENTREQUEST;
        }
    }

    getMessageStatus(): string{
        if (this.status == STATUS.FOLLOW){
            return "DEJAR DE SEGUIR";
        }else if (this.status == STATUS.SENT){
            return "CANCELAR ENVÍO DE SOLICITUD";
        }else if (this.status == STATUS.PENDING){
            return "ACEPTAR O DENEGAR SOLICITUD";
        }else if (this.status == STATUS.REQUEST){
            return "ENVIAR SOLICITUD"
        }
    }

    private paginationUserClass(){
        if(this._previous && this._next){
            this.previousUser = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6";
            this.nextUser = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6";
        }else if (!this._previous && this._next){
            this.previousUser = "";
            this.nextUser = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
        }else if(this._previous && !this._next){
            this.previousUser = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
            this.nextUser = "";
        }else{
            this.previousUser = "";
            this.nextUser = "";
        }
    }

    private paginationFriendClass(){
        if(this.mutualFriends.length > this.sizeMutualFriends && this.mutualFriends.length < this.totalMutualFriends){
            this.previousFriendClass = "col-xxl-7 col-xl-7 col-lg-7 col-md-5 col-sm-5 col-6";
            this.nextFriendClass = "col-xxl-5 col-xl-5 col-lg-5 col-md-5 col-sm-5 col-5";
        }else if (this.mutualFriends.length <= this.sizeMutualFriends && this.mutualFriends.length < this.totalMutualFriends){
            this.previousFriendClass = "";
            this.nextFriendClass = "col-xxl-7 col-xl-7 col-lg-7 col-md-12 col-sm-12 col-6";
        }else if(this.mutualFriends.length > this.sizeMutualFriends && this.mutualFriends.length >= this.totalMutualFriends){
            this.previousFriendClass = "col-xxl-8 col-xl-8 col-lg-8 col-md-12 col-sm-12 col-6";
            this.nextFriendClass = "";
        }else{
            this.previousFriendClass = "";
            this.nextFriendClass = "";
        }
    }

    private paginationGroupClass(){
        if(this.mutualGroups.length > this.sizeMutualGroups && this.mutualGroups.length < this.totalMutualGroups){
            this.previousGroupClass = "col-xxl-6 col-xl-5 col-lg-5 col-md-4 col-sm-5 col-6";
            this.nextGroupClass = "col-xxl-6 col-xl-7 col-lg-7 col-md-3 col-sm-5 col-5";
        }else if (this.mutualGroups.length <= this.sizeMutualGroups && this.mutualGroups.length < this.totalMutualGroups){
            this.previousGroupClass = "";
            this.nextGroupClass = "col-xxl-5 col-xl-5 col-lg-5 col-md-8 col-sm-8 col-4";
        }else if(this.mutualGroups.length > this.sizeMutualGroups && this.mutualGroups.length >= this.totalMutualGroups){
            this.previousGroupClass = "col-xxl-5 col-xl-5 col-lg-5 col-md-8 col-sm-8 col-4";
            this.nextGroupClass = "";
        }else{
            this.previousGroupClass = "";
            this.nextGroupClass = "";
        }
    }

    private paginationEventClass(){
        if(this.mutualEvents.length > this.sizeMutualEvents && this.mutualEvents.length < this.totalMutualEvents){
            this.previousEventClass = "col-xxl-5 col-xl-5 col-lg-5 col-md-4 col-sm-5 col-6";
            this.nextEventClass = "col-xxl-7 col-xl-7 col-lg-7 col-md-3 col-sm-5 col-5";
        }else if (this.mutualEvents.length <= this.sizeMutualEvents && this.mutualEvents.length < this.totalMutualEvents){
            this.previousEventClass = "";
            this.nextEventClass = "col-xxl-5 col-xl-5 col-lg-5 col-md-8 col-sm-8 col-4";
        }else if(this.mutualEvents.length > this.sizeMutualEvents && this.mutualEvents.length >= this.totalMutualEvents){
            this.previousEventClass = "col-xxl-5 col-xl-5 col-lg-5 col-md-8 col-sm-8 col-4";
            this.nextEventClass = "";
        }else{
            this.previousEventClass = "";
            this.nextEventClass = "";
        }
    }

    onRequest($event){
        if ($event){
            if (this.status == STATUS.PENDING) {
                let mUserUser: MUserUser = new MUserUser();
                mUserUser.userId = this.user.userId;
                mUserUser.friendId = this.authenticationService.getUser().id;
                mUserUser.accept = true;
                this.userUserService.editUserUser(mUserUser).subscribe();
            }else if (this.status == STATUS.REQUEST){
                let mUserUser: MUserUser = new MUserUser();
                mUserUser.userId = this.authenticationService.getUser().id;
                mUserUser.friendId = this.user.userId;
                mUserUser.userUserActive = true;
                mUserUser.accept = false;
                this.userUserService.getDeletedUserUser(this.user.userId,this.authenticationService.getUser().id).subscribe((response)=>{
                    if (response != null && !response.userUserActive){
                        this.userUserService.editUserUser(mUserUser).subscribe();
                    }else{
                        this.userUserService.createUserUser(mUserUser).subscribe();
                    }
                });
            }else if (this.status == STATUS.SENT || this.status == STATUS.FOLLOW){
                this.deleteUserUser();
            }
        }else{
            if (this.status == STATUS.PENDING){
                this.deleteUserUser();
            }
        }
        this.eventMessage.emit();
    }

    private deleteUserUser(){
        this.userUserService.deleteUserUser(this.user.userId, this.authenticationService.getUser().id).subscribe();
    }

    getMutualUserGroups(userId:number){
        this.groupUserService.getPageableMutualGroups(userId, this.authenticationService.getUser().id,this.pageMutualGroups, this.sizeMutualGroups).subscribe((response) => {
            this.mutualGroups.push(...response);
            this.paginationGroupClass();
        });
    }

    getTotalMutualGroups(userId:number){
        this.groupUserService.countMutualGroups(userId, this.authenticationService.getUser().id).subscribe((number)=>{
            this.totalMutualGroups = number;
        });
    }

    getMoreMutualGroups(){
        this.pageMutualGroups +=1;
        if (this.mutualGroups.length < this.mutualGroupsStored.length){
            this.mutualGroups = this.mutualGroupsStored.slice(0,this.sizeMutualGroups*(this.pageMutualGroups+1));
        }else{
            this.getMutualUserGroups(this.user.userId);
        }
        this.paginationGroupClass();
    }

    getLessMutualGroups(){
        if (this.mutualGroupsStored.length != this.totalMutualGroups){
            this.mutualGroupsStored = this.mutualGroups;
            this.mutualGroups = this.mutualGroups.slice(0,this.sizeMutualGroups*this.pageMutualGroups);
        }else{
            this.mutualGroups = this.mutualGroupsStored.slice(0, this.sizeMutualGroups*this.pageMutualGroups);
        }
        this.pageMutualGroups -=1;
        this.paginationGroupClass();
    }

    getMutualFriends(userId:number){
        this.userUserService.getPageableMutualFriends(userId, this.authenticationService.getUser().id,this.pageMutualFriends, this.sizeMutualFriends).subscribe((response) => {
            this.mutualFriends.push(...response);
            this.paginationFriendClass();
        });
    }


    getTotalMutualFriends(userId:number){
        this.userUserService.countMutualFriends(userId, this.authenticationService.getUser().id).subscribe((number)=>{
            this.totalMutualFriends = number;
        });
    }

    getMoreMutualFriends(){
        this.pageMutualFriends +=1;
        if (this.mutualFriends.length < this.mutualFriendsStored.length){
            this.mutualFriends = this.mutualFriendsStored.slice(0,this.sizeMutualFriends*(this.pageMutualFriends+1));
        }else{
            this.getMutualFriends(this.user.userId);
        }
        this.paginationFriendClass();
    }


    getLessMutualFriends(){
        if (this.mutualFriendsStored.length != this.totalMutualFriends){
            this.mutualFriendsStored = this.mutualFriends;
            this.mutualFriends = this.mutualFriends.slice(0,this.sizeMutualFriends*this.pageMutualFriends);
        }else{
            this.mutualFriends = this.mutualFriendsStored.slice(0, this.sizeMutualFriends*this.pageMutualFriends);
        }
        this.pageMutualFriends -=1;
        this.paginationFriendClass();
    }

    getMutualEvents(userId:number){
        this.userEventService.getPageableMutualEvents(userId, this.authenticationService.getUser().id,this.pageMutualEvents, this.sizeMutualEvents).subscribe((response) => {
            this.mutualEvents.push(...response);
            this.paginationEventClass();
        });
    }

    getTotalMutualEvents(userId:number){
        this.userEventService.countMutualEvents(userId, this.authenticationService.getUser().id).subscribe((number)=>{
            this.totalMutualEvents = number;
        });
    }

    getMoreMutualEvents(){
        this.pageMutualEvents +=1;
        if (this.mutualEvents.length < this.mutualEventsStored.length){
            this.mutualEvents = this.mutualEventsStored.slice(0,this.sizeMutualEvents*(this.pageMutualEvents+1));
        }else{
            this.getMutualEvents(this.user.userId);
        }
        this.paginationEventClass();
    }

    getLessMutualEvents(){
        if (this.mutualEventsStored.length != this.totalMutualEvents){
            this.mutualEventsStored = this.mutualEvents;
            this.mutualEvents = this.mutualEvents.slice(0,this.sizeMutualEvents*this.pageMutualEvents);
        }else{
            this.mutualEvents = this.mutualEventsStored.slice(0, this.sizeMutualEvents*this.pageMutualEvents);
        }
        this.pageMutualEvents -=1;
        this.paginationEventClass();
    }
}

