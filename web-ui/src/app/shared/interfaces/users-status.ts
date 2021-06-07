/* *
 * Interface of users-status
 * 
 * */

export enum UserStatusValue {
    CHOOSING = "CHOOSING",
    READY = "READY",
    VOTING = "VOTING",
    DONE = "DONE",
  }

export type UsersStatus = {[key: number] : UserStatusValue};