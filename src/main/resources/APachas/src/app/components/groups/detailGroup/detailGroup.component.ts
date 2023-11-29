import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GroupService} from "../../../services/group.service";
import {GroupUserService} from "../../../services/groupUser.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {NotificationService} from "../../../modules/notification/services/notification.service";
import {MUser} from "../../../models/MUser";
import {MGroup} from "../../../models/MGroup";

@Component({
    selector: 'app-detailGroup',
    templateUrl: './detailGroup.component.html',
    styleUrls: ['./detailGroup.component.css']
})
export class DetailGroupComponent implements OnInit {

    @Output()
    eventDelete = new EventEmitter<boolean>();

    @Output()
    eventDetail = new EventEmitter<number>();

    defaultUserImage: string = "./assets/user16.jpg";
    defaultImage: string = "./assets/group7_2.jpg";

    groupMembers: MUser[] = [];
    groupMembersStored: MUser[] = [];
    pageMember = 0;
    sizeMember = 6;
    totalMembers: number=0;
    message: string = "";
    delete: string = "";

    previousUserGroup: string="";
    nextUserGroup: string="";

    _previous: boolean = false;
    _next: boolean = false;
    _userGroup: MGroup = new MGroup();

    constructor(private groupService: GroupService,
                private groupUserService: GroupUserService,
                private authenticationService: AuthenticationService,
                private notificationService: NotificationService
    ) {
    }

    ngOnInit() {
        this.paginationUserGroupClass();
    }

    messageValue(request: boolean){
        if (!request && this.authenticationService.getUser().id == this.userGroup.groupOwner){
            this.message = "¿Estás seguro de que deseas eliminar el grupo?";
        }else{
            this.message = "¿Estás seguro de que quieres salir del grupo?";
        }
    }

    get previous(){
        return this._previous;
    }

    @Input() set previous( previous: boolean){
        this._previous = previous;
        this.paginationUserGroupClass();
    }

    get next(){
        return this._next;
    }

    @Input() set next( next: boolean){
        this._next = next;
        this.paginationUserGroupClass();
    }


    get userGroup(){
        return this._userGroup;
    }

    @Input() set userGroup (userGroup: MGroup) {
        if (userGroup != undefined) {
            this._userGroup = userGroup;
            this.membersReset();
            if (this.authenticationService.getUser().id == this.userGroup.groupOwner){
                this.delete = "Eliminar";
            }else{
                this.delete = "Salir";
            }
            if (this.userGroup.groupId != null){
                this.getMembers(this.userGroup.groupId);
                this.getTotalMembers(this.userGroup.groupId);
            }else{
                this._userGroup = new MGroup();
            }
        }
        this.groupMembers = [];
        this.groupMembersStored = [];
        this.paginationUserGroupClass();
    }

    onDelete($event){
        if($event.valueOf()){
            if (this.userGroup.groupOwner == this.authenticationService.getUser().id){
                this.groupService.deleteGroup(this.userGroup.groupId).subscribe(()=>{
                        this.eventDelete.emit();
                        this.notificationService.success("Grupo eliminado", "Se ha eliminado el grupo correctamente.");

                    }
                );
            }else{
                this.groupUserService.deleteGroupUser(this.userGroup.groupId, this.authenticationService.getUser().id).subscribe(() => {
                    this.eventDelete.emit();
                    this.notificationService.success("Eliminado del grupo", "Ya no eres miembro de este grupo.");
                });
            }
        }
    }

    showButtons(): boolean{
        if (this.userGroup.groupOwner == this.authenticationService.getUser().id){
            return true;
        }else{
            return false;
        }
    }

    ownerLabel(userId:number):string{
        let value:string = "";
        if (userId == this.userGroup.groupOwner){
            value = "Administrador";
        }
        return value;
    }

    ownerBorder(userId: number):string{
        if(userId == this.userGroup.groupOwner){
            return "owner";
        }
    }

    setPageUserGroup(number: number){
        this.eventDetail.emit(number);
    }

    getMembers(groupId:number){
        this.groupUserService.getPageableMembers(groupId,this.pageMember, this.sizeMember).subscribe((response) => {
            this.groupMembers.push(...response);
        });
    }

    getTotalMembers(groupId:number){
        this.groupUserService.countMembers(groupId).subscribe((number)=>{
            this.totalMembers = number;
        });
    }

    getMoreMembers(){
        this.pageMember +=1;
        if (this.groupMembers.length < this.groupMembersStored.length){
            this.groupMembers = this.groupMembersStored.slice(0,this.sizeMember*(this.pageMember+1));
        }else{
            this.getMembers(this.userGroup.groupId);
        }
    }

    getLessMembers(){
        if (this.groupMembersStored.length != this.totalMembers){
            this.groupMembersStored = this.groupMembers;
            this.groupMembers = this.groupMembers.slice(0,this.sizeMember*this.pageMember);
        }else{
            this.groupMembers = this.groupMembersStored.slice(0, this.sizeMember*this.pageMember);
        }
        this.pageMember -=1;
    }

    membersReset(){
        this.groupMembers = [];
        this.groupMembersStored = [];
        this.pageMember = 0;
    }


    private paginationUserGroupClass(){
        if(this.previous && this.next){
            this.previousUserGroup = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6";
            this.nextUserGroup = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6";
        }else if (!this.previous && this.next){
            this.previousUserGroup = "";
            this.nextUserGroup = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
        }else if(this.previous && !this.next){
            this.previousUserGroup = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
            this.nextUserGroup = "";
        }else{
            this.previousUserGroup = "";
            this.nextUserGroup = "";
        }
    }
}