import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {MEvent} from "../models/MEvent";
import {Event} from "./entities/Event";
import {map} from "rxjs/operators";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {MUser} from "../models/MUser";
import {User} from "./entities/User";
import {APachasError} from "../modules/notification/entities";
import {UserEvent} from "./entities/UserEvent";
import {MUserEvent} from "../models/MUserEvent";
import {AuthenticationService} from "./authentication.service";

@Injectable({
    providedIn: 'root'
})
export class UserEventService {

    constructor(private http: HttpClient,
                private authenticationService: AuthenticationService) {
    }

    countMutualEvents(userId: number, authId: number): Observable<number> {
        return this.http.get<number>(`${environment.restApi}/usersEvents/count/mutual/${userId}/${authId}`);
    }

    getNotifications(authId: number): Observable<string[]> {
        if (authId != undefined){
            return this.http.get<string[]>(`${environment.restApi}/usersEvents/notifications/${authId}`);
        }
    }

    getPageableMutualEvents(userId: number, authId: number, page: number, size: number): Observable<MEvent[]> {
        return this.http.get<Event[]>(`${environment.restApi}/usersEvents/pageable/mutual/${userId}/${authId}?page=${page}&size=${size}`).pipe(
            map(events => events.map(this.mapEvent.bind(this)))
        );
    }

    getPageableSearchEvents(eventName: string, authId: number, page: number, size: number): Observable<MEvent[]>{
        return this.http.get<Event[]>(`${environment.restApi}/usersEvents/pageable/events/${eventName}/${authId}/?page=${page}&size=${size}`).pipe(
            map(events => events.map(this.mapEvent.bind(this)))
        );
    }

    getPageableSearchEventsWithFinished(eventName: string, authId: number, page: number, size: number): Observable<MEvent[]>{
        return this.http.get<Event[]>(`${environment.restApi}/usersEvents/pageable/withFinished/${eventName}/${authId}/?page=${page}&size=${size}`).pipe(
            map(events => events.map(this.mapEvent.bind(this)))
        );
    }

    countSearchEvents(eventName: string, authId: number): Observable<number> {
        return this.http.get<number>(`${environment.restApi}/usersEvents/count/events/${eventName}/${authId}`);
    }

    countSearchEventsWithFinished(eventName: string, authId: number): Observable<number> {
        return this.http.get<number>(`${environment.restApi}/usersEvents/count/withFinished/${eventName}/${authId}`);
    }

    countEvents(authId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersEvents/count/events/${authId}`);
    }

    sumTotalEventExpense(eventId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersEvents/sum/${eventId}`);
    }

    countEventsWithFinished(authId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersEvents/count/withFinished/${authId}`);
    }

    getPageableEvents(authId: number, page: number, size: number): Observable<MEvent[]> {
        return this.http.get<Event[]>(`${environment.restApi}/usersEvents/pageable/events/${authId}?page=${page}&size=${size}`).pipe(
            map(events => events.map(this.mapEvent.bind(this)))
        );
    }

    getPageableEventsWithFinished(authId: number, page: number, size: number): Observable<MEvent[]> {
        return this.http.get<Event[]>(`${environment.restApi}/usersEvents/pageable/withFinished/${authId}?page=${page}&size=${size}`).pipe(
            map(events => events.map(this.mapEvent.bind(this)))
        );
    }

    getPageablePartakers(eventId: number, page:number, size: number): Observable<MUser[]>{
        return this.http.get<User[]>(`${environment.restApi}/usersEvents/pageable/partakers/${eventId}?page=${page}&size=${size}`).pipe(
            map(users => users.map(this.mapUser.bind(this)))
        );
    }

    countPartakers(eventId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersEvents/count/partakers/${eventId}`);
    }

    deleteUserEvent(eventId: number, userId: number): Observable<void> {
        return this.http.delete<void>(`${environment.restApi}/usersEvents/${eventId}/${userId}`).pipe(
            APachasError.throwOnError('Fallo al eliminar participante del evento', `Por favor, inténtelo de nuevo`)
        );
    }

    createUserEvent(eventId: any, userId: number): Observable<void> {
        let accept = false;
        if (userId == this.authenticationService.getUser().id){
            accept = true;
        }
        return this.http.post<void>(`${environment.restApi}/usersEvents`,{
            "eventId":eventId,
            "userId": userId,
            "accept": accept,
            "debt": 0,
            "totalExpense": 0,
            "userEventActive": true,
            "userEventCreation": "",
            "userEventRemoval":""
        })
            .pipe(
                APachasError.throwOnError('Fallo al añadir participantes al evento', `Por favor, inténtelo de nuevo`)
            );
    }

    editUserEvent(eventId: number, userIds: number[]): Observable<void> {
        return this.http.put<void>(`${environment.restApi}/usersEvents/${eventId}`,userIds)
            .pipe(
                APachasError.throwOnError('Fallo al editar participantes del evento', `Por favor, inténtelo de nuevo`)
            );
    }

    editTotalExpense(mUserEvent: MUserEvent): Observable<void>{
        return this.http.put<void>(`${environment.restApi}/usersEvents/totalExpense/${mUserEvent.eventId}/${mUserEvent.userId}`, mUserEvent.totalExpense)
            .pipe(
                APachasError.throwOnError('Fallo al editar el dinero aportado por el participante autenticado del evento', `Por favor, inténtelo de nuevo`)
            );
    }

    editDebt(eventId: number, userId: number, userDebt: number): Observable<void>{
        return this.http.put<void>(`${environment.restApi}/usersEvents/debt/${eventId}/${userId}`, userDebt)
            .pipe(
                APachasError.throwOnError('Fallo al editar el dinero que debe el participante del evento', `Por favor, inténtelo de nuevo`)
            );
    }

    editStatus(eventId: number, authId: number): Observable<void>{
        return this.http.put<void>(`${environment.restApi}/usersEvents/status/${eventId}`, authId)
            .pipe(
                APachasError.throwOnError('Fallo al editar el estado de los participantes del evento', `Por favor, inténtelo de nuevo`)
            );
    }

    getPartakers(eventId: number): Observable<MUser[]>{
        return this.http.get<User[]>(`${environment.restApi}/usersEvents/${eventId}`).pipe(
            map(users => users.map(this.mapUser.bind(this)))
        );
    }

    getUserEvent(eventId: number, userId: number): Observable<MUserEvent>{
        return this.http.get<UserEvent>(`${environment.restApi}/usersEvents/${eventId}/${userId}`).pipe(
            map(this.mapUserEvent.bind(this))
        );
    }

    getPageableUserEvents(eventId: number, page: number, size: number): Observable<MUserEvent[]>{
        return this.http.get<UserEvent[]>(`${environment.restApi}/usersEvents/pageable/${eventId}/?page=${page}&size=${size}`).pipe(
            map(userEvents => userEvents.map(this.mapUserEvent.bind(this)))
        );
    }

    countUserEvents(eventId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersEvents/count/${eventId}`);
    }

    getPageableSearchUserEvents(userName: string, eventId: number, page: number, size: number): Observable<MUserEvent[]>{
        return this.http.get<UserEvent[]>(`${environment.restApi}/usersEvents/pageable/${userName}/${eventId}/?page=${page}&size=${size}`).pipe(
            map(userEvents => userEvents.map(this.mapUserEvent.bind(this)))
        );
    }

    countSearchUserEvents(userName: string, eventId: number): Observable<number> {
        return this.http.get<number>(`${environment.restApi}/usersEvents/count/${userName}/${eventId}`);
    }

    getOpenEvents(authId: number):Observable<MEvent[]>{
        return this.http.get<Event[]>(`${environment.restApi}/usersEvents/events/open/${authId}`).pipe(
            map(events => events.map(this.mapEvent.bind(this)))
        );
    }

    getClosedEvents(authId: number):Observable<MEvent[]>{
        return this.http.get<Event[]>(`${environment.restApi}/usersEvents/events/closed/${authId}`).pipe(
            map(events => events.map(this.mapEvent.bind(this)))
        );
    }



    private mapEvent(event: Event) : MEvent {
        return {
            eventId: event.eventId,
            eventName:event.eventName,
            eventDescription:event.eventDescription,
            eventStart: event.eventStart,
            eventEnd: event.eventEnd,
            eventLocation:event.eventLocation,
            eventPhoto:event.eventPhoto,
            eventOpen:event.eventOpen,
            eventOwner:event.eventOwner,
            eventActive: event.eventActive,
            eventCreation: event.eventCreation,
            eventRemoval: event.eventRemoval
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

    private mapUserEvent(userEvent: UserEvent) : MUserEvent {
        return {
            userId: userEvent.userId,
            eventId: userEvent.eventId,
            totalExpense: userEvent.totalExpense,
            debt: userEvent.debt,
            accept: userEvent.accept,
            userEventActive: userEvent.userEventActive,
            userEventRemoval: userEvent.userEventRemoval,
            userEventCreation: userEvent.userEventCreation
        }
    }
}