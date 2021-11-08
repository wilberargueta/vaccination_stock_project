export interface Response<C> {
    ok?: boolean;
    message?:string;
    dateTime?:string;
    content?: C;
}