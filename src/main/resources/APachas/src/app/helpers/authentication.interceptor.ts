import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthenticationService} from '../services/authentication.service';

@Injectable({
	providedIn: 'root'
})
export class AuthenticationInterceptor implements HttpInterceptor {

	constructor(public authenticationService: AuthenticationService) {}

  /*
  INTERCEPTOR -> tipo de servicio implementable que permite interceptar peticiones HTTP entrantes o salientes
  utilizando HTTPClient. Al interceptar la solicitud HTTP, podemos modificar o cambiar el valor de la solicitud.
  */
	intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		if (this.authenticationService.isGuest()) {
			return next.handle(request);
		}
		request = request.clone({
			setHeaders: {
			"Content-Type": "application/json",
			"Access-Control-Allow-Origin": "*",
			"Access-Control-Allow-Headers": "Content-Type",
			"Access-Control-Allow-Methods": "GET,POST,OPTIONS,DELETE,PUT",
			Authorization: this.authenticationService.getAuthorizationHeader()
			}
		});

		return next.handle(request);
	}
}
