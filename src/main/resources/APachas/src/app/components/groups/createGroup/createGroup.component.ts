import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GroupService} from "../../../services/group.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {MUser} from "../../../models/MUser";
import {GroupUserService} from "../../../services/groupUser.service";
import {UserUserService} from "../../../services/userUser.service";
import {DomSanitizer} from "@angular/platform-browser";
import {NotificationService} from "../../../modules/notification/services/notification.service";
import {MGroup} from "../../../models/MGroup";

@Component({
    selector: 'app-createGroup',
    templateUrl: './createGroup.component.html',
    styleUrls: ['./createGroup.component.css']
})
export class CreateGroupComponent implements OnInit {

    defaultImage = "./assets/group7_2.jpg";
    defaultUserImage = "./assets/user16.jpg";
    friends: MUser[];
    imageFormat: boolean;
    groupMembers: number[];
    imageColor:string="";
    imageText: string;
    title: string = "CREAR GRUPO";
    _userGroup: MGroup;
    @Output()
    eventSave = new EventEmitter<boolean>();

    constructor(private groupService: GroupService,
                private groupUserService: GroupUserService,
                private userUserService: UserUserService,
                private authenticationService: AuthenticationService,
                private sanitizer: DomSanitizer,
                private notificationService: NotificationService
    ) {
    }

    ngOnInit() {
        this.groupMembers = [];
        this.friends = [];
    }

    get userGroup(){
        return this._userGroup;
    }

    @Input() set userGroup(userGroup: MGroup){
        if (userGroup.groupId != undefined){
            this._userGroup = userGroup;
            this.title = "Editar grupo";
            this.getMembers();
        }else{
            this._userGroup = new MGroup();
            this.title = "Crear grupo";
        }
        this.groupMembers = [];
        this.getFriends();
    }

    onSubmit(){
        if (this.userGroup.groupId != undefined){
            this.onEdit();
        }else{
            this.onCreate();
        }
    }

    onCreate() {
            this.userGroup.groupOwner = this.authenticationService.getUser().id;
            this.groupMembers.push(this.authenticationService.getUser().id);
            this.groupService.createGroup(this.userGroup).subscribe((response) => {
                this.groupMembers.forEach((id)=> {
                    this.groupUserService.createGroupUser(response,id).subscribe(() =>{
                        this.eventSave.emit();
                    });
                });
                this.closeModal();
                document.getElementById("closeButton").click();
                this.notificationService.success("Nuevo grupo creado", "Se ha creado el grupo correctamente.");
            });
    }

    onEdit(){
        this.groupMembers.push(this.authenticationService.getUser().id);
        this.groupService.editGroup(this.userGroup).subscribe(() => {
            this.groupUserService.editGroupUser(this.userGroup.groupId, this.groupMembers).subscribe();
            this.eventSave.emit();
            this.closeModal();
            document.getElementById("closeButton").click();
            this.notificationService.success("Grupo editado", "Se ha editado el grupo correctamente.");
        });
    }

    getImage(event): any {
        const file = event.target.files[0];
        if (this.imageFormatTest(file)){
            this.getBase64(file).then((image: any) => {
                this.userGroup.groupPhoto = image.base;
            });
            this.imageFormat = true;
        }else{
            this.imageFormat = false;
        }
    }

    closeModal(){
        this.userGroup = new MGroup();
        this.groupMembers = [];
        this.imageFormat = null;
    }

    changeStyle($event){
        if ($event.type == "mouseover"){
            this.imageColor = "imageColor";
            this.imageText = "imageText";
        }else{
            this.imageColor = "";
            this.imageText= "";
        }
    }

    private imageFormatTest(file:any): boolean{
        if(file.type.toString() == "image/jpeg" || file.type.toString() == "image/png"){
            return true;
        }
        return false;
    }

    private getBase64 = async ($event: any) => new Promise((resolve, reject) => {
        try {
            const unsafeImg = window.URL.createObjectURL($event);
            const image = this.sanitizer.bypassSecurityTrustUrl(unsafeImg);
            const reader = new FileReader();
            reader.readAsDataURL($event);

            reader.onload = () => {
                resolve({
                    blob: $event,
                    image,
                    base: reader.result
                });
            };
            reader.onerror = error => {
                resolve({
                    blob: $event.blob,
                    image,
                    base: null
                });
            };
        } catch (e) {
            return null;
        }
    });

    private getFriends(){
        this.userUserService.getFriends(this.authenticationService.getUser().id).subscribe((response) => {
            this.friends = response;
        });
    }

    private getMembers(){
        this.groupUserService.getMembers(this.userGroup.groupId).subscribe((response) =>{
            response.forEach((user)=>{
                if(user.userId != this.userGroup.groupOwner){
                    this.groupMembers.push(user.userId);
                }
            });
            this.groupMembers = [... this.groupMembers];
        });

    }
}