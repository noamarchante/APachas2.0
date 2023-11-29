import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";
import {APachasError} from "../modules/notification/entities";
import {User} from "./entities/User";
import {map} from "rxjs/operators";
import {MUser} from "../models/MUser";
import {AuthUser} from "../models/AuthUser";
import {MVerifyEmail} from "../models/MVerifyEmail";
import {MRetrievePassword} from "../models/MRetrievePassword";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getPageableUsers(authId: number, page: number, size: number): Observable<MUser[]>{
    return this.http.get<User[]>(`${environment.restApi}/users/pageable/${authId}?page=${page}&size=${size}`).pipe(
        map(users => users.map(this.mapUser.bind(this)))
    );
  }

  getPageableSearchUsers(userLogin: string, authId: number, page: number, size: number): Observable<MUser[]>{
    return this.http.get<User[]>(`${environment.restApi}/users/pageable/${userLogin}/${authId}?page=${page}&size=${size}`).pipe(
        map(users => users.map(this.mapUser.bind(this)))
    );
  }

  countUsers(authId: number): Observable<number> {
    return this.http.get<number>(`${environment.restApi}/users/count/${authId}`);
  }

  countSearchUsers(userLogin: string, authId: number): Observable<number> {
    return this.http.get<number>(`${environment.restApi}/users/count/${userLogin}/${authId}`);
  }

  getUser(userLogin: string): Observable<MUser>{
    return this.http.get<User>(`${environment.restApi}/users/${userLogin}`).pipe(
        map(this.mapUser.bind(this))
    );
  }

  getUserById(userId: number): Observable<MUser>{
    return this.http.get<User>(`${environment.restApi}/users/authUser/${userId}`).pipe(
        map(this.mapUser.bind(this))
    );
  }

  getToken(userEmail: string): Observable<boolean>{
    return this.http.put<boolean>(`${environment.restApi}/users/token`,userEmail);
  }

  loginAvailable(login: string): Observable<boolean>{
    return this.http.get<boolean>(`${environment.restApi}/users/available/${login}`);
  }

  editUser(authUser: AuthUser): Observable<void> {
    return this.http.put<void>(`${environment.restApi}/users`,{
      "userId": authUser.id,
      "userName": authUser.name,
      "userSurname": authUser.surname,
      "userLogin": authUser.login,
      "userPassword": authUser.password,
      "userBirthday": authUser.birthday,
      "userPhoto": authUser.photo,
      "roles": "",
      "userEmail": "",
      "permissions": "",
      "userCreation": "",
      "userRemoval": "",
      "userActive": ""
    })
        .pipe(
            APachasError.throwOnError('Fallo al editar el usuario', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo`)
        );
  }

  createUser(mUser: MUser): Observable<void> {

    return this.http.post<void>(`${environment.restApi}/users`,{
      "userName": mUser.userName,
      "userSurname": mUser.userSurname,
      "userLogin": mUser.userLogin,
      "userPassword": mUser.userPassword,
      "userEmail": mUser.userEmail,
      "userBirthday": "",
      "userPhoto": null,
      "roles": "USER",
      "permissions": "",
      "userCreation": "",
      "userRemoval": "",
      "userActive": true
    })
      .pipe(
        APachasError.throwOnError('Fallo en el registro', `Los datos del formulario son incorrectos o el usuario ya existe. Por favor, inténtelo de nuevo`)
      );
  }

  sendRetrievePassword(mUser: MUser): Observable<void>{
    return this.http.put<void>(`${environment.restApi}/users/sendRetrievePasswordEmail`, {
      "userEmail": mUser.userEmail
    })
        .pipe(
            APachasError.throwOnError('Fallo al enviar email', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo`)
        );
  }

  verifyUser(mVerifyEmail: MVerifyEmail): Observable<void> {
    return this.http.put<void>(`${environment.restApi}/users/verify`, {
      "userEmail": mVerifyEmail.userEmail,
      "tokenPassword": mVerifyEmail.tokenPassword
    })
        .pipe(
            APachasError.throwOnError('Fallo al activar el usuario', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo`)
        );
  }

  retrievePassword(mRetrievePassword: MRetrievePassword): Observable<void> {
    return this.http.put<void>(`${environment.restApi}/users/retrievePassword`, {
      "userEmail": mRetrievePassword.userEmail,
      "tokenPassword": mRetrievePassword.tokenPassword,
      "newPassword": mRetrievePassword.newPassword
    })
        .pipe(
            APachasError.throwOnError('Fallo al activar el usuario', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo`)
        );
  }
  private mapUser(user: User) : MUser {
    return {
      userId: user.userId,
      userName: user.userName,
      userSurname: user.userSurname,
      userLogin: user.userLogin,
      userPassword: user.userPassword,
      userEmail: user.userEmail,
      userBirthday: user.userBirthday,
      userPhoto: user.userPhoto,
      roles: user.roles,
      permissions: user.permissions,
      userActive: user.userActive,
      userRemoval: user.userRemoval,
      userCreation: user.userCreation,
      userNotify: user.userNotify
    }
  }

}
