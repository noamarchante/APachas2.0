import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {map} from "rxjs/operators";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {MUser} from "../models/MUser";
import {User} from "./entities/User";
import {APachasError} from "../modules/notification/entities";
import {AuthenticationService} from "./authentication.service";
import {UserProduct} from "./entities/UserProduct";
import {MUserProduct} from "../models/MUserProduct";

@Injectable({
    providedIn: 'root'
})
export class UserProductService {

    constructor(private http: HttpClient,
                private authenticationService: AuthenticationService) {
    }

    getPageablePartakers(productId: number, page:number, size: number): Observable<MUser[]>{
        return this.http.get<User[]>(`${environment.restApi}/usersProducts/pageable/partakers/${productId}?page=${page}&size=${size}`).pipe(
            map(users => users.map(this.mapUser.bind(this)))
        );
    }

    getPartakers(productId: number): Observable<MUser[]>{
        return this.http.get<User[]>(`${environment.restApi}/usersProducts/partakers/${productId}`).pipe(
            map(users => users.map(this.mapUser.bind(this)))
        );
    }

    countPartakers(productId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersProducts/count/partakers/${productId}`);
    }

    deleteUserProduct(productId: number, userId: number): Observable<void> {
        return this.http.delete<void>(`${environment.restApi}/usersProducts/${productId}/${userId}`).pipe(
            APachasError.throwOnError('Fallo al eliminar participante del producto', `Por favor, inténtelo de nuevo`)
        );
    }

    createUserProduct(productId: any, userId: number): Observable<void> {
        let accept = false;
        if (userId == this.authenticationService.getUser().id){
            accept = true;
        }
        return this.http.post<void>(`${environment.restApi}/usersProducts`,{
            "productId":productId,
            "userId": userId,
            "accept": accept,
            "userProductActive": true,
            "userProductCreation": "",
            "userProductRemoval":""
        })
            .pipe(
                APachasError.throwOnError('Fallo al añadir participantes al producto', `Por favor, inténtelo de nuevo`)
            );
    }

    editUserProduct(productId: number, authId: number): Observable<void> {
        return this.http.put<void>(`${environment.restApi}/usersProducts`,{
            "productId": productId,
            "userId": authId,
            "userProductActive":true,
            "userProductCreation": "",
            "userProductRemoval":""
        })
            .pipe(
                APachasError.throwOnError('Fallo al editar participantes del producto', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo`)
            );
    }

    getUserProduct(productId: number, userId: number): Observable<MUserProduct>{
        return this.http.get<UserProduct>(`${environment.restApi}/usersProducts/${productId}/${userId}`).pipe(
            map(this.mapUserProduct.bind(this))
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

    private mapUserProduct(userProduct: UserProduct) : MUserProduct {
        return {
            productId: userProduct.productId,
            userId: userProduct.userId,
            userProductActive: userProduct.userProductActive,
            userProductRemoval: userProduct.userProductRemoval,
            userProductCreation: userProduct.userProductCreation
        }
    }
}