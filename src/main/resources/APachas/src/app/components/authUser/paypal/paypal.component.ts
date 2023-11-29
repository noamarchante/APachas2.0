import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { IPayPalConfig, ICreateOrderRequest } from 'ngx-paypal';
import {AuthenticationService} from "../../../services/authentication.service";

declare var paypal;

@Component({
    selector: 'app-paypal',
    templateUrl: './paypal.component.html',
    styleUrls: ['./paypal.component.css']
})
export class PaypalComponent implements OnInit {

    public payPalConfig: any;

    constructor(private authenticationService: AuthenticationService) {
    }

    @ViewChild('paypal', { static: true }) paypalElement: ElementRef;

    ngOnInit(): void {
      //  this.initConfig();
        //this.initConfigB();
        this.test();
    }
    paidFor = false;
    product = {
        price: 777.77,
        description: 'used couch, decent condition'
    };
    test() {
        paypal
        .Buttons({
            createOrder: (data, actions) => {
                return actions.order.create({
                    // advanced: {
                    //     commit: 'true'
                    // },
                    //
                    // style: {
                    //     label: 'paypal',
                    //     layout: 'horizontal',
                    //     shape: 'rect',
                    //     color: 'silver',
                    //     tagline: false
                    // },
                    intent: 'CAPTURE',
                    payer: {
                        email_address: "sb-clc3816042471@personal.example.com",
                    },
                    purchase_units: [
                        {
                            payee:{
                                email_address: "sb-jypul16089008@business.example.com"
                            },
                            amount: {
                                currency_code: 'EUR',
                                value: '5.00'
                            },
                            item_list: {
                            items: [
                                {
                                    name: "hat",
                                    sku: "1",
                                    price: "5.00",
                                    currency: 'EUR',
                                    quantity: "1",
                                    description: "Brown hat."

                                }]}
                        }
                    ]
                });
            },
            onApprove: async (data, actions) => {
                const order = await actions.order.capture();
                this.paidFor = true;
                console.log(order);
            },
            onError: err => {
                console.log(err);
            }
        })
        .render(this.paypalElement.nativeElement);


    }
    /*

    P
    sb-clc3816042471@personal.example.com
    rO>0F7Ge
    - TA2FJTAJMAWQU

    B
    sb-tioio15365415@business.example.com
    ?BRnDf#1
    - 7822BDYPJ54BG

    P
    sb-nn8cv16042503@personal.example.com
    Xj6=@w7P
    - HSL8KPUXAEHDU

    B
    sb-jypul16089008@business.example.com
    -C9=6Jb2
    - ZU6K2LR9KUYE6

    */

    private initConfigB(): void {
        this.payPalConfig = {
            createOrder: data =>
                <ICreateOrderRequest>{
                    intent: "CAPTURE",
                    // Pagador
                    payer: {
                        email_address: "sb-clc3816042471@personal.example.com",
                    },

                    purchase_units: [
                        {
                            amount: {
                                currency_code: "EUR",
                                value: "9.99"
                            },
                            payee:{
                                email_address: "sb-jypul16089008@business.example.com"
                            },
                            items: [
                            ]
                        }
                    ]
                },
            currency: 'EUR',
            clientId: 'ATf1-QPk20tqgmCXMEH_rTkdpYDJjOoUSHsoP4ROOevzgsYVLnvurgPeAGh8PAynWpSe0NKJiTolOThs',
            advanced: {
                commit: 'true'
            },

            style: {
                label: 'paypal',
                layout: 'horizontal',
                shape: 'rect',
                color: 'silver',
                tagline: false
            },
            onApprove: (data, actions) => {
                console.log(
                    "onApprove - transaction was approved, but not authorized",
                    data,
                    actions
                );
                actions.order.get().then(details => {
                    console.log(
                        "onApprove - you can get full order details inside onApprove: ",
                        details
                    );
                });
            },
            onClientAuthorization: data => {
                console.log(
                    "onClientAuthorization - you should probably inform your server about completed transaction at this point",
                    data
                );
            },
            onCancel: (data, actions) => {
                console.log("OnCancel", data, actions);
            },
            onError: err => {
                console.log("OnError", err);
            },
            onClick: (data, actions) => {
                console.log("onClick", data, actions);
            }
        };
    }

    private initConfig(): void {
        this.payPalConfig = {
            currency: 'EUR',
            clientId: 'ATf1-QPk20tqgmCXMEH_rTkdpYDJjOoUSHsoP4ROOevzgsYVLnvurgPeAGh8PAynWpSe0NKJiTolOThs',
            advanced: {
                commit: 'true'
            },

            style: {
                label: 'paypal',
                layout: 'horizontal',
                shape: 'rect',
                color: 'silver',
                tagline: false
            },


            createOrderOnClient: (data) => <ICreateOrderRequest>{
                description: "Holaa",
                // Pagador
                payer: {
                     email_address: "sb-clc3816042471@personal.example.com",
                },
                // Al que se paga
                // payee:{
                //     email_address: "sb-nn8cv16042503@personal.example.com",
                // },
                intent: 'CAPTURE',
                purchase_units: [
                    {
                        payee:{
                            email_address: "sb-nn8cv16042503@personal.example.com"
                        },
                        amount: {
                            currency_code: 'EUR',
                            value: '9.99'
                        },
                        items: [
                            // {
                            //     name: 'Enterprise Subscription',
                            //     quantity: '1',
                            //     category: 'DIGITAL_GOODS',
                            //     unit_amount: {
                            //         currency_code: 'EUR',
                            //         value: '9.99',
                            //     },
                            // }
                        ]
                    }
                ]
            },

            onApprove: (data, actions) => {
                console.log('onApprove - transaction was approved, but not authorized', data, actions);
                actions.order.get().then(details => {
                    console.log('onApprove - you can get full order details inside onApprove: ', details);
                });
            },
            onClientAuthorization: (data) => {
                console.log('onClientAuthorization - you should probably inform your server about completed transaction at this point', data);
                //this.showSuccess = true;
            },
            onCancel: (data, actions) => {
                console.log('OnCancel', data, actions);
            },
            onError: err => {
                console.log('OnError', err);
            },
            onClick: (data, actions) => {
                console.log('onClick', data, actions);
            },
        };
    }
}