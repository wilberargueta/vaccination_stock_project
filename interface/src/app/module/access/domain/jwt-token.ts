import { Rol } from "./rol";

export interface JwtToken {
    token?: string;
    token_type?: string;
    expires?: string;
    username?: string;
    role?: Rol;
    id?: number;
}
