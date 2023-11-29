import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
    selector: 'app-messageConfirm',
    templateUrl: './messageConfirm.component.html',
    styleUrls: ['./messageConfirm.component.css']
})
export class MessageConfirmComponent implements OnInit {

    @Output()
    eventMessageConfirm = new EventEmitter<boolean>();

    _message: string = "";

    constructor() {
    }

    ngOnInit() {}

    get message(){
        return this._message;
    }

    @Input() set message(message: string){
        if (message!= undefined){
            this._message = message;
        }
    }

    onConfirm(value: boolean){
        this.eventMessageConfirm.emit(value);
    }
}