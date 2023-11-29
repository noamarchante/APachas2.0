import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from "../../../services/user.service";
import {MUser} from "../../../models/MUser";
import {AuthenticationService} from "../../../services/authentication.service";
import {NotificationService} from "../../../modules/notification/services/notification.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {
  private mUser: MUser = new MUser();
  name = "";
  surname = "";
  login = "";
  password = "";
  passwordConfirm = "";
  email = "";
  available: boolean = true;
  private return = 'login';

  constructor(private router: Router,
              private userService: UserService,
              private notificationService: NotificationService) {
  }
  ngOnInit() {}
  onCreate(){
    this.mUser.userLogin = this.login;
    this.mUser.userPassword = this.password;
    this.mUser.userName = this.name;
    this.mUser.userEmail = this.email;
    this.mUser.userSurname = this.surname;
    if(this.password == this.passwordConfirm) {
      this.userService.createUser(this.mUser).subscribe(()=>{
        this.notificationService.info("Revisa tu correo electrónico y verifica tu cuenta para poder acceder a A Pachas", "Pendiente de verificación");
      });
    }
  }

  loginAvailable(){
    this.userService.loginAvailable(this.login).subscribe((response) => {
      if (!response) {
        this.available = false;
      }else{
        this.available = true;
      }
    });
  }
}
