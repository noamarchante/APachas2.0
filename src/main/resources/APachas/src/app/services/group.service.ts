import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";
import {APachasError} from "../modules/notification/entities";
import {MGroup} from "../models/MGroup";

@Injectable({
    providedIn: 'root'
})
export class GroupService {

    constructor(private http: HttpClient) { }

    createGroup(mGroup: MGroup): Observable<number> {
        return this.http.post<number>(`${environment.restApi}/groups`,{
            "groupId":mGroup.groupId,
            "groupName": mGroup.groupName,
            "groupDescription": mGroup.groupDescription,
            "groupPhoto": mGroup.groupPhoto,
            "groupCreation":"",
            "groupRemoval": "",
            "groupOwner": mGroup.groupOwner,
            "groupActive": true
        })
            .pipe(
                APachasError.throwOnError('Fallo al crear el grupo', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo.`)
            );
    }

    editGroup(mGroup: MGroup): Observable<void> {
        return this.http.put<void>(`${environment.restApi}/groups`,{
            "groupId":mGroup.groupId,
            "groupName": mGroup.groupName,
            "groupDescription": mGroup.groupDescription,
            "groupPhoto": mGroup.groupPhoto,
            "groupCreation":"",
            "groupRemoval": "",
            "groupOwner": mGroup.groupOwner,
            "groupActive": null
        })
            .pipe(
                APachasError.throwOnError('Fallo al editar el grupo', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo.`)
            );
    }

    deleteGroup(groupId: number): Observable<void> {
        return this.http.delete<void>(`${environment.restApi}/groups/${groupId}`).pipe(
            APachasError.throwOnError('Fallo al eliminar grupo', `Por favor, inténtelo de nuevo más tarde.`)
        );
    }
}
