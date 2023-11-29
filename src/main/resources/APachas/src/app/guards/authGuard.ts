import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthenticationService} from "../services/authentication.service";
/*
Es un servicio injectable que devuelve true si el usuario puede acceder a una ruta o false si no puede.
 */
@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {
	constructor(private router: Router) {
	}

	canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

		if (localStorage.getItem('currentUser')) {
			if (route.url.toString() == "products" && localStorage.getItem("products") == undefined) {
				this.router.navigate(['/events']);
				return false;
			}

			if (route.url.toString() == "transactions" && localStorage.getItem("transactions") == undefined) {
				this.router.navigate(['/events']);
				return false;
			}

			if (route.url.toString() != "products"){
				localStorage.removeItem("products");
			}
			if (route.url.toString() != "transactions"){
				localStorage.removeItem("transactions");
			}

			if (route.url.toString() != "profile"){
				localStorage.removeItem("profile");
			}
			return true;
		}
		this.router.navigate(['/login']);

		return false;
	}
}
