import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {MEvent} from "../models/MEvent";
import {APachasError} from "../modules/notification/entities";
import {Event} from "./entities/Event";
import {map} from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class EventService {

    constructor(private http: HttpClient) { }

    createEvent(mEvent: MEvent): Observable<number> {
        return this.http.post<number>(`${environment.restApi}/events`,{
            "eventId": mEvent.eventId,
            "eventName":mEvent.eventName,
            "eventDescription":mEvent.eventDescription,
            "eventStart": mEvent.eventStart,
            "eventEnd": mEvent.eventEnd,
            "eventLocation":mEvent.eventLocation,
            "eventPhoto":mEvent.eventPhoto,
            "eventOpen": "",
            "eventOwner":mEvent.eventOwner,
            "eventActive": "",
            "eventCreation": "",
            "eventRemoval": ""
        })
            .pipe(
                APachasError.throwOnError('Fallo al crear el evento', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo.`)
            );
    }

    editEvent(mEvent: MEvent): Observable<void> {
        return this.http.put<void>(`${environment.restApi}/events`,{
            "eventId": mEvent.eventId,
            "eventName":mEvent.eventName,
            "eventDescription":mEvent.eventDescription,
            "eventStart": mEvent.eventStart,
            "eventEnd": mEvent.eventEnd,
            "eventLocation":mEvent.eventLocation,
            "eventPhoto":mEvent.eventPhoto,
            "eventOpen":mEvent.eventOpen,
            "eventOwner":mEvent.eventOwner,
            "eventActive": mEvent.eventActive,
            "eventCreation": "",
            "eventRemoval": ""
        })
            .pipe(
                APachasError.throwOnError('Fallo al editar el evento', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo.`)
            );
    }

    editOpen(eventId: number, open: boolean): Observable<void>{
        return this.http.put<void>(`${environment.restApi}/events/open/${eventId}`, open)
            .pipe(
                APachasError.throwOnError('Fallo al cerrar el evento', `Por favor, inténtelo de nuevo más tarde.`)
            );
    }

    deleteEvent(eventId: number): Observable<void> {
        return this.http.delete<void>(`${environment.restApi}/events/${eventId}`).pipe(
            APachasError.throwOnError('Fallo al eliminar el evento', `Por favor, inténtelo de nuevo más tarde.`)
        );
    }

    getEvent(eventId: number):Observable<MEvent>{
        return this.http.get<Event>(`${environment.restApi}/events/${eventId}`).pipe(
            map(this.mapEvent.bind(this))
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
}