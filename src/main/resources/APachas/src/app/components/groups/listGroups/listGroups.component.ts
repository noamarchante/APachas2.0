import {Component, OnInit} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";
import {AuthenticationService} from "../../../services/authentication.service";
import {MGroup} from "../../../models/MGroup";
import {GroupUserService} from "../../../services/groupUser.service";

@Component({
    selector: 'app-groups',
    templateUrl: './listGroups.component.html',
    styleUrls: ['./listGroups.component.css']
})
export class ListGroupsComponent implements OnInit {
    groupName = "";
    groups: MGroup[] = [];
    images: {[index:number]: any;} = {};
    defaultImage = "./assets/group7_2.jpg";
    totalPage:number= 0;
    page: number= 0;
    selectedUserGroup: MGroup = new MGroup();
    size: number= 6;
    index: number;
    previousClass:string;
    nextClass:string;
    pageDirection: number;

    constructor(private groupUserService: GroupUserService,
                private authenticationService: AuthenticationService,
                private sanitizer: DomSanitizer) {}

    ngOnInit() {
        this.getGroups();
        this.paginationClass();
    }

    setGroup(){
        this.selectedUserGroup = new MGroup();
    }

    selectUserGroup(index:number){
        this.selectedUserGroup = this.groups[index];
        this.index = index;
    }

    setSelectedUserGroupPage(){
        if (this.pageDirection != undefined){
            if (this.pageDirection == -1){
                this.index = this.size-1;
                this.selectedUserGroup = this.groups[this.index];
            }else if (this.pageDirection == 1){
                this.index = 0;
                this.selectedUserGroup = this.groups[this.index];
            }
        }
    }

    getGroups(){
        this.groupUserService.getPageableGroups(this.authenticationService.getUser().id, this.page, this.size).subscribe((response) => {
            this.groups = response;
            this.setSelectedUserGroupPage();
            this.getURL(response);
            this.totalPages();
        });
    }

    paginationClass(){
        if(this.page!=0 && this.page+1<this.totalPage){
            this.previousClass = "col-xxl-9 col-xl-9 col-lg-9 col-md-9 col-sm-9 col-6";
            this.nextClass = "col-xxl-3 col-xl-3 col-lg-3 col-md-3 col-sm-3 col-6";
        }else if (this.page==0 && this.page+1<this.totalPage){
            this.previousClass = "";
            this.nextClass = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12";
        }else if(this.page!=0 && this.page+1==this.totalPage){
            this.previousClass = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12";
            this.nextClass = "";
        }else{
            this.previousClass = "";
            this.nextClass = "";
        }
    }

    setPage(page: number){
        this.page += page;
        this.pagination();
        this.paginationClass();
    }

    private pagination(){
        if(this.groupName == ""){
            this.getGroups();
        }else{
            this.search();
        }
    }

    private getURL(groups: MGroup[]){
        groups.forEach((group) => {
            this.images[group.groupId] = this.sanitizer.bypassSecurityTrustUrl(group.groupPhoto);
        });
    }

    private search(){
        this.groupUserService.getPageableSearchGroups(this.groupName, this.authenticationService.getUser().id, this.page, this.size).subscribe((response) => {
            this.groups = response;
            this.setSelectedUserGroupPage();
            this.searchTotalPages();
            this.getURL(response);
        });
    }

    searchInput(){
        this.page=0;
        this.pagination();
    }

    private totalPages() {
        this.groupUserService.countGroups(this.authenticationService.getUser().id).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }

    private searchTotalPages(){
        this.groupUserService.countSearchGroups(this.groupName, this.authenticationService.getUser().id).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }

    getNext (): boolean{
        if (this.page != this.totalPage-1 || this.groups[this.index+1] != undefined){
            return true;
        }else{
            return false;
        }
    }

    getPrevious():boolean{
        if (this.page != 0 || this.groups[this.index-1] != undefined){
            return true;
        }else{
            return false;
        }
    }

    setSelectedUserGroup(event: number) {
        this.pageDirection = event.valueOf();
        if ( this.groups[this.index + event.valueOf()] != undefined){
                this.selectUserGroup(this.index + event.valueOf());
        }else{
            this.setPage(event.valueOf());

        }
    }
}