import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";
import {NotificationService} from "../../../modules/notification/services/notification.service";
import {UserEventService} from "../../../services/userEvent.service";
import {MUserEvent} from "../../../models/MUserEvent";

@Component({
    selector: 'app-createUserEventExpense',
    templateUrl: './createUserEventExpense.component.html',
    styleUrls: ['./createUserEventExpense.component.css']
})
export class CreateUserEventExpenseComponent implements OnInit {
    _userEvent: MUserEvent;
    @Output()
    eventSave = new EventEmitter<boolean>();
    operation: string[] = [];
    showOperation: string ="";
    totalExpense: number;

    constructor(private userEventService: UserEventService,
                private sanitizer: DomSanitizer,
                private notificationService: NotificationService
    ) {
    }

    ngOnInit() {}

    get userEvent(){
        return this._userEvent;
    }

    @Input() set userEvent(userEvent: MUserEvent){
        if (userEvent != undefined){
            this._userEvent = userEvent;
            this.totalExpense = this.userEvent.totalExpense;
            this.operation = [];
            this.operation[0] = userEvent.totalExpense.toString();
            this.showOperation = userEvent.totalExpense.toString();
        }else{
            this._userEvent = new MUserEvent();
            this.totalExpense = 0;
            this.operation = [];
            this.showOperation = "";
        }
    }

    onEdit(){
        this.userEventService.editTotalExpense(this.userEvent).subscribe(() => {
            this.eventSave.emit();
            this.closeModal();
            document.getElementById("closeButton").click();
            this.notificationService.success("Dinero añadido al evento", "Se ha editado el dinero añadido correctamente.");
        });
    }

    calculator(value: number, operator: string){
        //si el valor seleccionado es un numero
        if(value != -1){
            //si ya tenia el primer numero de la operacion pero no tenia el operador
            if (this.operation[0] != null && this.operation[1] == null){
                //si el primer numero es 0 se resetea con el nuevo numero
                if (this.operation[0] == "0"){
                    this.operation[0] = value.toString();
                    this.showOperation = value.toString();
                    this.userEvent.totalExpense = value;
                }else{ //si el primer numero no es cero se añade cifra al numero
                    this.operation[0] += value.toString();
                    this.showOperation += value.toString();
                    if (this.showOperation.includes(".") && !this.userEvent.totalExpense.toString().includes(".")){
                        this.userEvent.totalExpense = parseFloat(this.userEvent.totalExpense.toString() + "."+ value.toString());

                    }else{
                        this.userEvent.totalExpense = parseFloat(this.userEvent.totalExpense.toString() + value.toString());
                    }
                }
            }else if (this.operation[0] != null && this.operation[1] != null){ //si ya tenia el primer numero y el operador
                if (this.operation[2] == null){ //si no tengo la segunda cifra asigno el nuevo numero a la segunda cifra
                    this.operation[2] = value.toString();
                }else{ //si tengo la segunda cifra sigo añadiendo numeros
                    this.operation[2] += value.toString();
                }
                this.showOperation += value.toString();
            }
        }else{ //si el valor seleccionado no es un numero
            if (operator == "R"){ //si selecciono r vuelvo a los valores iniciales
                this.operation = [];
                this.operation[0] = this.totalExpense.toString();
                this.showOperation = this.totalExpense.toString();
                this.userEvent.totalExpense = this.totalExpense;
            }else if (operator == "C") { //si selecciono c reseteo la operacion
                this.operation = [];
                this.operation[0] = this.userEvent.totalExpense.toString();
                this.showOperation = this.userEvent.totalExpense.toString();
            }else{ //si es otro operador
                    if (this.operation[0] != null){ //si la primera cifra tiene valor
                        if (this.operation[2] != null){ //si la segunda cifra tiene un valor
                            if (operator == "."){ //si selecciono . añado el operador a la segunda cifra
                                this.operation[2] += operator.toString();
                                this.showOperation += operator;
                            }else if (operator == "="){ //si el operador es un = lo asigno al tercer operando y calculo la operacion
                                this.showOperation += operator;
                                this.operation[3] = operator;
                                if (this.operation[1].toString() == "+"){ //si el operando es un + sumo
                                    this.userEvent.totalExpense = parseFloat((parseFloat(this.operation[0]) + parseFloat(this.operation[2])).toFixed(2));
                                }else if (this.operation[1].toString() == "-"){ //si el operando es un - resto
                                    this.userEvent.totalExpense = parseFloat((parseFloat(this.operation[0]) - parseFloat(this.operation[2])).toFixed(2));
                                }else if (this.operation[1].toString() == "/"){ //si el operando es un / divido
                                    this.userEvent.totalExpense = parseFloat((parseFloat(this.operation[0]) / parseFloat(this.operation[2])).toFixed(2));
                                }else if (this.operation[1].toString() == "*"){ //si el operando es un * multiplico
                                    this.userEvent.totalExpense = parseFloat((parseFloat(this.operation[0]) * parseFloat(this.operation[2])).toFixed(2));
                                }
                                this.operation = [];
                                this.operation[0] = this.userEvent.totalExpense.toString();
                                this.showOperation = this.userEvent.totalExpense.toString();
                            }
                        }else if(this.operation[1] == null && operator != "." && operator != "="){ //si el operador es nulo y el operador no es . o = lo asigno al operador
                            this.showOperation += operator;
                            this.operation[1] = operator;
                        }else if (this.operation[1] == null && operator == "." && this.operation[0] != null){
                            this.operation[0] += operator.toString();
                            this.showOperation += operator;
                        }
                    }
                }
        }
    }

    closeModal(){
        this.operation = [];
        this.operation[0] = this.totalExpense.toString();
        this.showOperation = this.totalExpense.toString();
        this.userEvent.totalExpense = this.totalExpense;

    }
}