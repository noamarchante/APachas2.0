import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {APachasError} from "../modules/notification/entities";
import {MProduct} from "../models/MProduct";
import {Group} from "./entities/Group";
import {map} from "rxjs/operators";
import {Product} from "./entities/Product";
import {MEvent} from "../models/MEvent";

@Injectable({
    providedIn: 'root'
})
export class ProductService {

    event: MEvent;
    constructor(private http: HttpClient) { }

    createProduct(mProduct: MProduct): Observable<number> {
        return this.http.post<number>(`${environment.restApi}/products`,{
            "productId": mProduct.productId,
            "productName":mProduct.productName,
            "productDescription":mProduct.productDescription,
            "productPurchaseDate": mProduct.productPurchaseDate,
            "productCost":mProduct.productCost,
            "productQuantity":mProduct.productQuantity,
            "productPhoto": mProduct.productPhoto,
            "eventId": mProduct.eventId,
            "eventActive": "",
            "eventCreation": "",
            "eventRemoval": ""
        })
            .pipe(
                APachasError.throwOnError('Fallo al crear el producto', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo.`)
            );
    }

    editProduct(mProduct: MProduct): Observable<void> {
        return this.http.put<void>(`${environment.restApi}/products`,{
            "productId": mProduct.productId,
            "productName":mProduct.productName,
            "productDescription":mProduct.productDescription,
            "productPurchaseDate": mProduct.productPurchaseDate,
            "productCost":mProduct.productCost,
            "productQuantity":mProduct.productQuantity,
            "productPhoto": mProduct.productPhoto,
            "eventId": mProduct.eventId,
            "eventActive": "",
            "eventCreation": "",
            "eventRemoval": ""
        })
            .pipe(
                APachasError.throwOnError('Fallo al editar el producto', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo`)
            );
    }

    deleteProduct(productId: number): Observable<void> {
        return this.http.delete<void>(`${environment.restApi}/products/${productId}`).pipe(
            APachasError.throwOnError('Fallo al eliminar producto', `Por favor, inténtelo de nuevo`)
        );
    }

    getPageableSearchProducts(productName: string, eventId: number, page: number, size: number): Observable<MProduct[]>{
        return this.http.get<Product[]>(`${environment.restApi}/products/pageable/${productName}/${eventId}?page=${page}&size=${size}`).pipe(
            map(products => products.map(this.mapProduct.bind(this)))
        );
    }

    countSearchProducts(productName: string, eventId: number): Observable<number> {
        return this.http.get<number>(`${environment.restApi}/products/count/${productName}/${eventId}`);
    }

    countProducts(eventId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/products/count/${eventId}`);
    }

    sumTotalProductCost(eventId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/products/sum/${eventId}`);
    }

    getPageableProducts(eventId: number, page: number, size: number): Observable<MProduct[]> {
        return this.http.get<Group[]>(`${environment.restApi}/products/pageable/${eventId}?page=${page}&size=${size}`).pipe(
            map(products => products.map(this.mapProduct.bind(this)))
        );
    }

    getProducts(eventId: number): Observable<MProduct[]> {
        return this.http.get<Group[]>(`${environment.restApi}/products/${eventId}`).pipe(
            map(products => products.map(this.mapProduct.bind(this)))
        );
    }

    getAllProductsPartakers(eventId: number): Observable<boolean> {
        return this.http.get<boolean>(`${environment.restApi}/products/allProductsPartakers/${eventId}`);
    }

    private mapProduct(product: Product) : MProduct {
        return {
            productId: product.productId,
            productName: product.productName,
            productDescription: product.productDescription,
            productPhoto: product.productPhoto,
            productQuantity: product.productQuantity,
            productCost: product.productCost,
            eventId: product.eventId,
            productPurchaseDate: product.productPurchaseDate,
            productCreation: product.productCreation,
            productRemoval: product.productRemoval,
            productActive: product.productActive
        }
    }
}