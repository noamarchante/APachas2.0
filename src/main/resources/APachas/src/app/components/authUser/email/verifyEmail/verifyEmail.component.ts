import {AfterViewChecked, Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../../services/user.service";
import {AuthenticationService} from "../../../../services/authentication.service";
import {MVerifyEmail} from "../../../../models/MVerifyEmail";
@Component({
    selector: 'app-verifyEmail',
    templateUrl: './verifyEmail.component.html',
    styleUrls: ['./verifyEmail.component.css']
})
export class VerifyEmailComponent implements OnInit{

    mVerifyEmail: MVerifyEmail = new MVerifyEmail();

    message = "ERROR DE VERIFICACIÓN. Inténtelo de nuevo mas tarde o solicite un nuevo enlace";
    messageColor = "messageColor";

    constructor(private router: Router,
                private userService: UserService,
                private activatedRoute: ActivatedRoute) {
    }

    ngOnInit() {
        this.mVerifyEmail.userEmail = this.activatedRoute.snapshot.queryParams["email"];
        this.mVerifyEmail.tokenPassword = this.activatedRoute.snapshot.queryParams["token"];
        this.userService.getToken(this.mVerifyEmail.userEmail).subscribe((response) =>{
            if (response){
                this.verifyEmail();
            }else{
                this.getMessage(false);
            }
        });
    }

    verifyEmail(){
        this.userService.verifyUser(this.mVerifyEmail).subscribe(()=>{
            this.getMessage(true);
        });
    }

    private getMessage(goodRequest: boolean){
        if (goodRequest){
            this.message = "Tu cuenta ha sido verificada";
            this.messageColor = "";
        }else{
            this.message = "ERROR DE VERIFICACIÓN. Inténtelo de nuevo mas tarde o solicite un nuevo enlace";
            this.messageColor = "messageColor";
        }
    }

}