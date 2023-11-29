import {AfterViewChecked, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";
import {NotificationService} from "../../../../modules/notification/services/notification.service";
import {AuthenticationService} from "../../../../services/authentication.service";
import {AuthUser} from "../../../../models/AuthUser";
import {UserService} from "../../../../services/user.service";
import {Router} from "@angular/router";


@Component({
    selector: 'app-editProfile',
    templateUrl: './editProfile.component.html',
    styleUrls: ['./editProfile.component.css']
})
export class EditProfileComponent implements OnInit, AfterViewChecked {
    defaultImage = "./assets/user16.jpg";
    imageFormat: boolean;
    imageColor:string="";
    imageText: string;
    title: string = "EDITAR USUARIO";
    _authUser: AuthUser;
    birthday: Date;
    password = "";
    newPassword = "";
    passwordConfirm = "";
    login = "";
    available: boolean = true;

    public options: any = {
        autoApply: false,
        alwaysShowCalendars: true,
        applyButtonClasses: "btn-primary applyDate",
        buttonClasses: "btn btn-sm",
        cancelClass: "btn-default cancelDate",
        drops: "up",
        locale: {
            format: 'DD/MM/yyyy',
            "firstDay": 1,
            daysOfWeek: [
                "D",
                "L",
                "M",
                "X",
                "J",
                "V",
                "S"
            ],
            monthNames: [
                "Enero",
                "Febrero",
                "Marzo",
                "Abril",
                "Mayo",
                "Junio",
                "Julio",
                "Agosto",
                "Septiembre",
                "Octubre",
                "Noviembre",
                "Diciembre"
            ]
        },
        minDate: undefined,
        maxDate: new Date(),
        opens: 'left',
        showDropdowns: true,
        timePicker: false,
        singleDatePicker: true
    };


    constructor(private sanitizer: DomSanitizer,
                private authenticationService: AuthenticationService,
                private userService: UserService,
                private router: Router,
                private notificationService: NotificationService
    ) {
    }

    ngOnInit() {
    }

    get authUser(){
        return this._authUser;
    }

    @Input() set authUser(authUser: AuthUser){
        if (authUser.id != undefined){
            this._authUser = authUser;
            if (this.authUser.birthday != null){
                this.birthday = new Date(this.authUser.birthday);
            }else{
                this.birthday = null;
            }
            this.login = authUser.login;
        }else{
            this._authUser = new AuthUser();
            this.birthday = null;
        }
    }

    setNotify(){
        this.authUser.notify = !this.authUser.notify;
    }

    ngAfterViewChecked() {
        document.getElementsByClassName("applyDate")[0].textContent ="Aplicar";
        document.getElementsByClassName("cancelDate")[0].textContent = "Cancelar";
    }

    public applyDate(): void {
        this.authUser.birthday= new Date(this.birthday.valueOf()).toJSON().replace("T", " ").replace("Z", "");
    }

    public selectedDate(value: any): void {
        this.birthday = new Date(value.start);
    }

    public cancelDate(): void {
        this.birthday = undefined;
    }

    onEdit(){
        if (this.correctPassword()){
            this.authUser.login = this.login;
            this.userService.editUser(this.authUser).subscribe(() => {
                this.closeModal();
                document.getElementById("closeButton").click();
                localStorage.setItem("profile", JSON.stringify(true));
                this.authenticationService.logOut();
                this.router.navigateByUrl("/login");
            });
        }
    }

    getImage(event): any {
        const file = event.target.files[0];
        if (this.imageFormatTest(file)){
            this.getBase64(file).then((image: any) => {
                this.authUser.photo = image.base;
            });
            this.imageFormat = true;
        }else{
            this.imageFormat = false;
        }
    }

    correctPassword(): boolean{
        if(this.password != "" && this.password != null) {
            if (this.password == this.authUser.password) {
                if (this.newPassword == this.passwordConfirm && this.newPassword != "" && this.newPassword != null && this.passwordConfirm != "" && this.passwordConfirm != null) {
                    this.authUser.password = this.newPassword;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }else{
            return true;
        }
    }

    closeModal(){
        this.imageFormat = null;
        this.authUser = new AuthUser();
        this.cancelDate();
        this.password = "";
        this.newPassword = "";
        this.passwordConfirm = "";
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

}