import { ErrorHandler, Injectable } from '@angular/core';
import { NotificationService } from '../services/notification.service';
import { HttpErrorResponse } from '@angular/common/http';
import { APachasError } from '../entities';

@Injectable()
export class ErrorNotificationHandler implements ErrorHandler {
	constructor(
		private notificationService: NotificationService
		) {
		}

	public handleError(error: Error | APachasError | HttpErrorResponse): void {
		if (console) {
			console.log(error);
		}

		if (error instanceof APachasError) {
			console.log('CAUSE', error.cause);
			this.notificationService.error(error.detail, error.summary);
		} else if (error instanceof HttpErrorResponse) {
			this.notificationService.error(error.error, error.statusText);
		}
	}
}
