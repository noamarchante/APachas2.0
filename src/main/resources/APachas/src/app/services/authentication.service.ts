 import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {AuthUser} from '../models/AuthUser';
import {APachasError} from '../modules/notification/entities';
import {UserService} from "./user.service";
 import {NotificationService} from "../modules/notification/services/notification.service";

//SERVICE -> Se encarga de acceder a los datos para entregarlos a los componentes
@Injectable({
  providedIn: 'root'
})

//ACCEDE A LOS DATOS NECESARIOS PARA LA AUTENTICACION
export class AuthenticationService {

	private user: AuthUser = new AuthUser();

	constructor(private  http: HttpClient,  private userService: UserService,private notificationService: NotificationService) {}

  //COMPRUEBA SI LAS CREDENCIALES SON CORRECTAS
	checkCredentials(login: string, password: string) {

		this.user.login = login;
		this.user.password = password;

		return this.http.post<void>(`${environment.restApi}/login`, {
		  "username": this.user.login,
		  "password": this.user.password
		}, {observe:"response" as "body", responseType: 'json'}
		)
		  .pipe(
			APachasError.throwOnError('Failed to login', `User or password incorrect. Please try again.`)
		  );
	}

  //CONFIGURA AL USUARIO LOGGEADO
	public logIn(login: string, password: string, authorization: string) {
		this.user.login = login;
		this.user.password = password;
		this.user.authHeader = authorization;
		this.user.authenticated = true;
		this.setAuthUser(login);
	}

	//RECOGER OTROS DATOS DEL USUARIO Y COMPROBAR SI ESTA ACTIVO PARA AUTENTICAR
	public setAuthUser(login: string){
		this.userService.getUser(login).subscribe((response) => {
			if (response.userActive){
				this.user.id = response.userId;
				this.user.email = response.userEmail;
				if (this.user.birthday !=null){
					this.user.birthday = response.userBirthday.toString();
				}
				this.user.name = response.userName;
				this.user.surname = response.userSurname;
				this.user.permission = response.permissions;
				this.user.rol = response.roles;
				this.user.photo = response.userPhoto;
				this.user.notify = response.userNotify;
				this.user.save();
			}else{
				this.notificationService.warning("Activa tu cuenta con el email que has recibido en la cuenta proporcionada","Cuenta no activada");
			}
		});
	}


	/*getEditUser(): AuthUser{
		this.userService.getUserById(this.user.id).subscribe((response) => {
			this.user.email = response.userEmail;
			this.user.birthday = response.userBirthday.toString();
			this.user.name = response.userName;
			this.user.surname = response.userSurname;
			this.user.permission = response.permissions;
			this.user.rol = response.roles;
			this.user.photo = response.userPhoto;
			this.user.login = response.userLogin;
			this.user.password = response.userPassword;
			this.user.save();
			return this.user;
		});
		return null;
	}*/

	//CIERRE DE SESION
	public logOut() {
		this.user.clear();
		this.user = new AuthUser();
	}

	//CABECERA DE LA AUTORIZACION => TOKEN
	public getAuthorizationHeader(): string {
		return this.getUser().authHeader;
	}

	//DEVUELVE EL USUARIO AUTENTICADO
	public getUser(): AuthUser {
		return this.user;
	}

	//COMPRUEBA SI ES UN INVITADO
	public isGuest(): boolean {
		return !this.user.authenticated;
	}

}
