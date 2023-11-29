import {Component, OnInit,ChangeDetectionStrategy, ViewChild, TemplateRef,} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from "../../../../services/authentication.service";
import {AuthUser} from "../../../../models/AuthUser";
import {DomSanitizer} from "@angular/platform-browser";
import {startOfDay, endOfDay, subDays, addDays, endOfMonth, isSameDay, isSameMonth, addHours,} from 'date-fns';
import { Subject } from 'rxjs';
import {CalendarEvent, CalendarView,} from 'angular-calendar';
import {UserEventService} from "../../../../services/userEvent.service";
import {EventService} from "../../../../services/event.service";
import {toNumbers} from "@angular/compiler-cli/src/diagnostics/typescript_version";

const colors: any = {
    blue: {
        primary: '#007eff',
        secondary: '',
    },
    orange: {
        primary: '#ff9e34',
        secondary: '',
    },
    red: {
        primary: '#d92e2e',
        secondary: '',
    }
};

@Component({
    selector: 'app-profile',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './detailProfile.component.html',
    styleUrls: ['./detailProfile.component.css']
})
export class DetailProfileComponent implements OnInit {
    authUser: AuthUser;
    imageProfile: any = null;
    defaultImage: string = "./assets/user16.jpg";

    @ViewChild('modalContent', { static: true }) modalContent: TemplateRef<any>;

    view: CalendarView = CalendarView.Month;

    CalendarView = CalendarView;

    viewDate: Date = new Date();

    refresh = new Subject<void>();

    events: CalendarEvent[] = [];

    activeDayIsOpen: boolean = false;

    constructor(private router: Router,
                private authenticationService: AuthenticationService,
                private sanitizer: DomSanitizer,
                private userEventService: UserEventService,
                private eventService: EventService) {
    }

    ngOnInit() {
        this.authUser = this.authenticationService.getUser();
        this.getURLProfile();
        this.getOpenEvents();
        this.getClosedEvents();

    }

    private getURLProfile() {
        this.imageProfile = this.sanitizer.bypassSecurityTrustUrl(this.authUser.photo);
    }

    //mostras eventos
    dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
        if (isSameMonth(date, this.viewDate)) {
            if ((isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) || events.length === 0) {
                this.activeDayIsOpen = false;
            } else {
                this.activeDayIsOpen = true;
            }
            this.viewDate = date;
        }
    }

    //evento seleccionado
    handleEvent(event: CalendarEvent): void {
       this.eventService.getEvent(toNumbers(event.id.valueOf().toString())[0]).subscribe((response) =>{
            localStorage.setItem("products",  JSON.stringify(response));
            this.router.navigateByUrl("/products");
        });
    }

    //añade los eventos a las distintas vistas
    addEvent(): void {
        this.events = [
            ...this.events
        ];
    }

    //cambia de vista
    setView(view: CalendarView) {
        this.view = view;
    }

    //estable si se muestran los detalles de los eventos del dia al cambiar de dia seleccionado
    closeOpenMonthViewDay() {
        this.activeDayIsOpen = false;
    }

    //coger los eventos abiertos de un usuario
    getOpenEvents(){
        this.userEventService.getOpenEvents(this.authUser.id).subscribe((response)=>{
            response.forEach((event) =>{
                this.events.push({
                    id: event.eventId,
                    start: addHours(new Date(event.eventStart),2),
                    end: addHours(new Date(event.eventEnd),2),
                    title: event.eventName,
                    color: colors.blue,
                });
            });
        });

    }

    //coger los eventos cerrados de un usuario
    getClosedEvents(){
        this.userEventService.getClosedEvents(this.authUser.id).subscribe((response) => {
            response.forEach((event) => {
                this.events.push({
                    id: event.eventId,
                    start: addHours(new Date(event.eventStart),2),
                    end: addHours(new Date(event.eventEnd),2),
                    title: event.eventName,
                    color: colors.orange,
                });
            })
        });
    }



}
