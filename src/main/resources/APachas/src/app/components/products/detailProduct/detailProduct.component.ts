import {AfterViewChecked, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AuthenticationService} from "../../../services/authentication.service";
import {NotificationService} from "../../../modules/notification/services/notification.service";
import {MUser} from "../../../models/MUser";
import {MProduct} from "../../../models/MProduct";
import {ProductService} from "../../../services/product.service";
import {UserProductService} from "../../../services/userProduct.service";
import {PRODUCTJOIN} from "../listProducts/listProducts.component";
import {UserEventService} from "../../../services/userEvent.service";

@Component({
    selector: 'app-detailProduct',
    templateUrl: './detailProduct.component.html',
    styleUrls: ['./detailProduct.component.css']
})
export class DetailProductComponent implements OnInit, AfterViewChecked {

    @Output()
    eventDelete = new EventEmitter<boolean>();

    @Output()
    eventDetail = new EventEmitter<number>();

    @Output()
    statusUpdate = new EventEmitter<number>();

    defaultUserImage: string = "./assets/user16.jpg";
    defaultImage: string = "./assets/product3.jpg";

    productPartakers: MUser[] = [];
    productPartakersStored: MUser[] = [];
    pagePartaker = 0;
    sizePartaker = 6;
    totalPartaker: number=0;
    message: string = "";
    previousProduct: string="";
    nextProduct: string="";

    _previous: boolean = false;
    _next: boolean = false;
    _open: boolean = true;
    _product: MProduct = new MProduct();
    _status: string ="";

    constructor(private productService: ProductService,
                private userProductService: UserProductService,
                private userEventService: UserEventService,
                private authenticationService: AuthenticationService,
                private notificationService: NotificationService
    ) {
    }

    ngOnInit() {
        this.paginationProductClass();
    }

    ngAfterViewChecked() {
        document.getElementsByClassName("applyDate")[0].textContent ="Aplicar";
        document.getElementsByClassName("cancelDate")[0].textContent = "Cancelar";
    }

    messageValue(){
        this.message = "¿Estás seguro de que deseas eliminar el producto?";
    }

    get previous(){
        return this._previous;
    }

    @Input() set previous( previous: boolean){
        this._previous = previous;
        this.paginationProductClass();
    }

    get open(){
        return this._open;
    }

    @Input() set open( open: boolean){
        this._open = open;
    }

    get next(){
        return this._next;
    }

    @Input() set next( next: boolean){
        this._next = next;
        this.paginationProductClass();
    }

    get status(){
        return this._status;
    }

    @Input() set status( status: string){
        if (status != undefined){
            this._status = status;
        }
    }


    get product(){
        return this._product;
    }

    @Input() set product (product: MProduct) {
        if (product != undefined) {
            this._product = product;
            this.partakerReset();
            if (this.product.productId != null){
                this.getPartakers(this.product.productId);
                this.getTotalPartakers(this.product.productId);
            }else{
                this._product = new MProduct();
            }
        }
        this.productPartakers = [];
        this.productPartakersStored = [];
        this.paginationProductClass();
    }

    onDelete($event){
        if($event.valueOf()){
            this.productService.deleteProduct(this.product.productId).subscribe(()=>{
                this.userProductService.deleteUserProduct(this.product.productId, this.authenticationService.getUser().id).subscribe(()=>{});
                    this.eventDelete.emit();
                this.notificationService.success("Producto eliminado", "Se ha eliminado el producto correctamente.");
            });
        }
    }

    setPageProduct(number: number){
        this.eventDetail.emit(number);
    }

    getPartakers(productId:number){
        this.userProductService.getPageablePartakers(productId,this.pagePartaker, this.sizePartaker).subscribe((response) => {
            this.productPartakers.push(...response);
        });
    }

    getTotalPartakers(productId:number){
        this.userProductService.countPartakers(productId).subscribe((number)=>{
            this.totalPartaker = number;
        });
    }

    getMorePartakers(){
        this.pagePartaker +=1;
        if (this.productPartakers.length < this.productPartakersStored.length){
            this.productPartakers = this.productPartakersStored.slice(0,this.sizePartaker*(this.pagePartaker+1));
        }else{
            this.getPartakers(this.product.productId);
        }
    }

    getLessPartakers(){
        if (this.productPartakersStored.length != this.totalPartaker){
            this.productPartakersStored = this.productPartakers;
            this.productPartakers = this.productPartakers.slice(0,this.sizePartaker*this.pagePartaker);
        }else{
            this.productPartakers = this.productPartakersStored.slice(0, this.sizePartaker*this.pagePartaker);
        }
        this.pagePartaker -=1;
    }

    partakerReset(){
        this.productPartakers = [];
        this.productPartakersStored = [];
        this.pagePartaker = 0;
    }

    private paginationProductClass(){
        if(this._previous && this._next){
            this.previousProduct = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6";
            this.nextProduct = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6";
        }else if (!this._previous && this._next){
            this.previousProduct = "";
            this.nextProduct = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
        }else if(this._previous && !this._next){
            this.previousProduct = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
            this.nextProduct = "";
        }else{
            this.previousProduct = "";
            this.nextProduct = "";
        }
    }

    setStatus(){
        this.statusUpdate.emit();
        if (this.status == PRODUCTJOIN.JOIN){
            this.userProductService.getUserProduct(this.product.productId, this.authenticationService.getUser().id).subscribe((response)=>{
                if (response.productId != 0){
                    this.userProductService.editUserProduct(this.product.productId, this.authenticationService.getUser().id).subscribe();
                }else{
                    this.userProductService.createUserProduct(this.product.productId, this.authenticationService.getUser().id).subscribe();
                }
            });
        }else{
            this.userProductService.deleteUserProduct(this.product.productId, this.authenticationService.getUser().id).subscribe(()=>{
            });
        }
    }
}