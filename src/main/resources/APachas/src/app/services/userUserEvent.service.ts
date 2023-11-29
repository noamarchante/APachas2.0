import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {map} from "rxjs/operators";
import {UserUserEvent} from "./entities/UserUserEvent";
import {MUserUserEvent} from "../models/MUserUserEvent";
import {APachasError} from "../modules/notification/entities";
import {MUser} from "../models/MUser";
import {User} from "./entities/User";

@Injectable({
    providedIn: 'root'
})
export class UserUserEventService {

    constructor(private http: HttpClient) { }

    createUserUserEvent(eventId: any): Observable<void> {
        return this.http.post<void>(`${environment.restApi}/usersUsersEvents`, eventId)
            .pipe(
                APachasError.throwOnError('Fallo al añadir transacciones', `Por favor, inténtelo de nuevo`)
            );
    }

    getPageableUserUserEventsByEvent(eventId: number, page: number, size: number): Observable<MUserUserEvent[]>{
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/byEvent/${eventId}?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPartakers(authId: number): Observable<MUser[]>{
        return this.http.get<User[]>(`${environment.restApi}/usersUsersEvents/partakers/${authId}`).pipe(
            map(users => users.map(this.mapUser.bind(this)))
        );
    }

    editPaid(transaction: MUserUserEvent, paid: boolean): Observable<void>{
        return this.http.put<void>(`${environment.restApi}/usersUsersEvents/paid/${transaction.eventId}/${transaction.senderId}/${transaction.receiverId}`, paid)
            .pipe(
                APachasError.throwOnError('Fallo al actualizar el pago', `Por favor, inténtelo de nuevo`)
            );
    }

    editConfirmed(transaction: MUserUserEvent, confirmed: boolean): Observable<void>{
        return this.http.put<void>(`${environment.restApi}/usersUsersEvents/confirmed/${transaction.eventId}/${transaction.senderId}/${transaction.receiverId}`, confirmed)
            .pipe(
                APachasError.throwOnError('Fallo al actualizar la confirmación', `Por favor, inténtelo de nuevo`)
            );
    }

    getPageableUserUserEventsByAuthUser(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]>{
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/byAuthUser/${authUserId}?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableUserUserEventsByAuthUserByEvent(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]> {
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/byAuthUser/${authUserId}/byEvent?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableUserUserEventsDebtsByAuthUser(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]>{
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/debts/byAuthUser/${authUserId}?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableUserUserEventsDebtsByAuthUserByEvent(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]>{
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/debts/byAuthUser/${authUserId}/byEvent?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableUserUserEventsPaymentsByAuthUser(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]>{
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/payments/byAuthUser/${authUserId}?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableUserUserEventsPaymentsByAuthUserByEvent(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]>{
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/payments/byAuthUser/${authUserId}/byEvent?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableNotFinishedUserUserEventsByAuthUser(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]>{
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/notFinished/byAuthUser/${authUserId}?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableNotFinishedUserUserEventsByAuthUserByEvent(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]> {
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/notFinished/byAuthUser/${authUserId}/byEvent?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableNotFinishedUserUserEventsDebtsByAuthUser(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]> {
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/notFinished/debts/byAuthUser/${authUserId}?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableNotFinishedUserUserEventsDebtsByAuthUserByEvent(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]> {
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/notFinished/debts/byAuthUser/${authUserId}/byEvent?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableNotFinishedUserUserEventsPaymentsByAuthUser(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]> {
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/notFinished/payments/byAuthUser/${authUserId}?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableNotFinishedUserUserEventsPaymentsByAuthUserByEvent(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]> {
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/notFinished/payments/byAuthUser/${authUserId}/byEvent?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableUserUserEventsByAuthUserByDate(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]> {
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/byAuthUser/${authUserId}/byDate?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableUserUserEventsDebtsByAuthUserByDate(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]> {
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/debts/byAuthUser/${authUserId}/byDate?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableUserUserEventsPaymentsByAuthUserByDate(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]> {
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/payments/byAuthUser/${authUserId}/byDate?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableNotFinishedUserUserEventsByAuthUserByDate(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]> {
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/notFinished/byAuthUser/${authUserId}/byDate?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableNotFinishedUserUserEventsDebtsByAuthUserByDate(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]> {
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/notFinished/debts/byAuthUser/${authUserId}/byDate?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableNotFinishedUserUserEventsPaymentsByAuthUserByDate(authUserId: number,page: number, size: number): Observable<MUserUserEvent[]> {
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/notFinished/payments/byAuthUser/${authUserId}/byDate?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    countUserUserEventsByEvent(eventId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersUsersEvents/count/byEvent/${eventId}`);
    }

    countUserUserEventsByAuthUser(authId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersUsersEvents/count/byAuthUser/${authId}`);
    }

    countUserUserEventsDebtsByAuthUser(authId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersUsersEvents/count/debts/byAuthUser/${authId}`);
    }

    countUserUserEventsPaymentsByAuthUser(authId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersUsersEvents/count/payments/byAuthUser/${authId}`);
    }

    countFinishedUserUserEventsByAuthUser(authId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersUsersEvents/count/finished/byAuthUser/${authId}`);
    }

    countFinishedUserUserEventsDebtsByAuthUser(authId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersUsersEvents/count/finished/debts/byAuthUser/${authId}`);
    }

    countFinishedUserUserEventsPaymentsByAuthUser(authId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersUsersEvents/count/finished/payments/byAuthUser/${authId}`);
    }

    getPageableSearchUserUserEventsByEvent(transactionActorName: string, eventId: number, page: number, size: number): Observable<MUserUserEvent[]>{
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/byEvent/${transactionActorName}/${eventId}/?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    getPageableSearchUserUserEventsByAuthUser(transactionActorName: string, authId: number, page: number, size: number): Observable<MUserUserEvent[]>{
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/byAuthUser/${transactionActorName}/${authId}/?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    countSearchUserUserEventsByEvent(transactionActorName: string, eventId: number): Observable<number> {
        return this.http.get<number>(`${environment.restApi}/usersUsersEvents/count/byEvent/${transactionActorName}/${eventId}`);
    }


    countSearchUserUserEventsByAuthUser(transactionActorName: string, authId: number): Observable<number> {
        return this.http.get<number>(`${environment.restApi}/usersUsersEvents/count/byAuthUser/${transactionActorName}/${authId}`);
    }

    private mapUserUserEvent(userUserEvent: UserUserEvent) : MUserUserEvent {
        return {
            senderId: userUserEvent.senderId,
            receiverId: userUserEvent.receiverId,
            eventId: userUserEvent.eventId,
            confirmed: userUserEvent.confirmed,
            cost: userUserEvent.cost,
            paid: userUserEvent.paid,
            userUserEventActive: userUserEvent.userUserEventActive,
            userUserEventRemoval: userUserEvent.userUserEventRemoval,
            userUserEventCreation: userUserEvent.userUserEventCreation
        }
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
