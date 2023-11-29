import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../services/user.service";
import {MUser} from "../../../../models/MUser";
import {NotificationService} from "../../../../modules/notification/services/notification.service";


@Component({
    selector: 'app-retrievePasswordEmail',
    templateUrl: './retrievePasswordEmail.component.html',
    styleUrls: ['./retrievePasswordEmail.component.css']
})
export class RetrievePasswordEmailComponent implements OnInit {

    mUser: MUser= new MUser();
    title: string = "RECUPERAR CONTRASEÑA";
    email: string = "";


    constructor(private userService: UserService,
                private notificationService: NotificationService) {
    }

    ngOnInit() {
    }

    onSubmit(){
        this.mUser.userEmail = this.email;
        this.userService.sendRetrievePassword(this.mUser).subscribe(()=>{
            document.getElementById("closeButton").click();
            this.notificationService.info("Revisa tu correo electrónico para establecer una nueva contraseña", "Revisa tu correo electrónico");
            this.closeModal();
        });
    }

    closeModal(){
        this.email = "";
        this.mUser = new MUser();
    }
}