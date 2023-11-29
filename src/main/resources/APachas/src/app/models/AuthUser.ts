//USUARIO AUTENTICADO

export class AuthUser {
  private _authHeader: string;
  private _authenticated: boolean;
  private userLogin: string;
  private userPassword: string;
  private userEmail: string;
  private roles: string;
  private permissions: string;
  private userId: number;
  private userName: string;
  private userSurname: string;
  private userBirthday: string;
  private userPhoto: string;
  private userNotify: boolean;

  constructor() {
    const user: AuthUser = JSON.parse(<string>localStorage.getItem('currentUser'));
    if (user != null) {
      this.userId = user.userId;
      this.userLogin = user.userLogin;
      this.userPassword = user.userPassword;
      this.userEmail = user.userEmail;
      this._authenticated = user._authenticated;
      this._authHeader = user._authHeader;
      this.roles = user.roles;
      this.permissions = user.permissions;
      this.userName = user.userName;
      this.userSurname = user.userSurname;
      this.userBirthday = user.userBirthday;
      this.userPhoto = user.userPhoto;
      this.userNotify = user.userNotify;
    } else {
      this._authenticated = false;
    }
  }

  get authHeader(): string {
    return this._authHeader;
  }

  set authHeader(value: string) {
    this._authHeader = value;
  }

  get authenticated(): boolean {
    return this._authenticated;
  }

  set authenticated(value: boolean) {
    this._authenticated = value;
  }

  get email(): string {
    return this.userEmail;
  }

  set email(value: string) {
    this.userEmail = value;
  }

  get name(): string {
    return this.userName;
  }

  set name(value: string) {
    this.userName = value;
  }

  get surname(): string {
    return this.userSurname;
  }

  set surname(value: string) {
    this.userSurname = value;
  }

  get birthday(): string {
    return this.userBirthday;
  }

  set birthday(value: string) {
    this.userBirthday = value;
  }

  get photo(): string {
    return this.userPhoto;
  }

  set photo(value: string) {
    this.userPhoto = value;
  }

  get login(): string {
    return this.userLogin;
  }

  set login(value: string) {
    this.userLogin = value;
  }

  get password(): string {
    return this.userPassword;
  }

  set password(value: string) {
    this.userPassword = value;
  }

  get rol(): string {
    return this.roles;
  }

  set rol(value: string) {
    this.roles = value;
  }

  get permission(): string {
    return this.permissions;
  }

  set permission(value: string) {
    this.permissions = value;
  }

  get id(): number {
    return this.userId;
  }

  set id(value: number) {
    this.userId = value;
  }

  get notify(): boolean {
    return this.userNotify;
  }

  set notify(value: boolean) {
    this.userNotify = value;
  }

  //ALMACENA AL USUARIO LOGGEADO ASOCIADO A LA CLAVE currentUser EN FORMATO JSON
  public save() {
    localStorage.setItem('currentUser', JSON.stringify(this));
  }

  //ELIMINA EL USUARIO LOGGEADO
  public clear() {
    localStorage.removeItem('currentUser');
  }
}