import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../services/user.service";
import {UserUserService} from "../../../services/userUser.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {DomSanitizer} from "@angular/platform-browser";
import {MUser} from "../../../models/MUser";


export enum STATUS {
    REQUEST = 'Solicitar amistad', PENDING = 'Solicitud pendiente', FOLLOW = 'Siguiendo', SENT = 'Solicitud enviada'
}

@Component({
    selector: 'app-users',
    templateUrl: './listUsers.component.html',
    styleUrls: ['./listUsers.component.css']
})
export class ListUsersComponent implements OnInit {
    login = "";
    users: MUser[] = [];
    images: {[index:number]: any;} = {};
    defaultImage = "./assets/user16.jpg";
    friends: {[index: number]: any;} = {};
    totalPage = 0;
    page = 0;
    private size = 6;
    previous:string;
    next:string;
    selectedUsers: MUser;
    pageDirection: number;
    index:number;
    status:string="";
    selectedUser: MUser = new MUser();

    constructor(private userService: UserService,
                private userUserService: UserUserService,
                private authenticationService: AuthenticationService,
                private sanitizer: DomSanitizer) {}

    ngOnInit() {
        this.getUsers();
        this.paginationClass();
    }

    setUser(){
        this.selectedUser = new MUser();
    }

    selectUser(index:number){
        this.selectedUser = this.users[index];
        this.index = index;
        this.status = this.friends[this.selectedUser.userId];
    }

    setSelectedUserPage(){
        if (this.pageDirection != undefined){
            if (this.pageDirection == -1){
                this.index = this.size-1;
                this.selectedUser = this.users[this.index];
            }else if (this.pageDirection == 1){
                this.index = 0;
                this.selectedUser = this.users[this.index];
            }
        }
    }

    getUsers(){
        this.userService.getPageableUsers(this.authenticationService.getUser().id, this.page, this.size).subscribe((response) => {
            this.users = response;
            this.setSelectedUserPage();
            this.getURL(response);
            this.getStatus(response);
            this.totalPages();
        });
    }

    paginationClass(){
        if(this.page!=0 && this.page+1<this.totalPage){
            this.previous = "col-xxl-9 col-xl-9 col-lg-9 col-md-9 col-sm-2 col-6";
            this.next = "col-xxl-3 col-xl-3 col-lg-3 col-md-3 col-sm-2 col-6";
        }else if (this.page==0 && this.page+1<this.totalPage){
            this.previous = "";
            this.next = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12";
        }else if(this.page!=0 && this.page+1==this.totalPage){
            this.previous = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12";
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
        if(this.login == ""){
            this.getUsers();
        }else{
            this.searchUsers();
        }
    }

    private getURL(users: MUser[]){
        users.forEach((user) => {
            this.images[user.userId] = user.userPhoto;
        });
    }

    private searchUsers(){
        this.userService.getPageableSearchUsers(this.login, this.authenticationService.getUser().id, this.page, this.size).subscribe((response) => {
            this.users = response;
            this.getStatus(response);
            this.searchTotalPages();
            this.getURL(response);
        });
    }

    searchInput(){
        this.page=0;
        this.pagination();
    }

    private totalPages() {
        this.userService.countUsers(this.authenticationService.getUser().id).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }

    private searchTotalPages(){
        this.userService.countSearchUsers(this.login, this.authenticationService.getUser().id).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }

    getNext (): boolean{
        if (this.page != this.totalPage-1 || this.users[this.index+1] != undefined){
            return true;
        }else{
            return false;
        }
    }

    getPrevious():boolean{
        if (this.page != 0 || this.users[this.index-1] != undefined){
            return true;
        }else{
            return false;
        }
    }

    setSelectedUser(event: number) {
        this.pageDirection = event.valueOf();
        if ( this.users[this.index + event.valueOf()] != undefined){
            this.selectUser(this.index + event.valueOf());
        }else{
            this.setPage(event.valueOf());

        }
    }

    private statusValue (statusBD: boolean, friend: boolean): string {
        let status: string;
        if (statusBD) {
            status = STATUS.FOLLOW;
        } else if (friend) {
            status = STATUS.PENDING;
        }else if (!statusBD){
            status = STATUS.SENT;
        }else{
            status = STATUS.REQUEST;
        }
        return status;
    }

    private getStatus (mUsers: MUser[]) {
        mUsers.forEach((mUser) => {
            this.userUserService.getUserUser(mUser.userId, this.authenticationService.getUser().id).subscribe((response) => {
                if (response != null) {
                    this.friends[mUser.userId] = this.statusValue(response.accept,false);
                } else {
                    this.friendStatus(mUser);
                }
            });
        });
    }

    private friendStatus(mUser: MUser){
        this.userUserService.getUserUser(this.authenticationService.getUser().id,mUser.userId).subscribe((response) => {
                if (response != null) {
                    this.friends[mUser.userId] = this.statusValue(response.accept, true);
                }else{
                    this.friends[mUser.userId] = STATUS.REQUEST;
                }
            });
    }
}