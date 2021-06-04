/* *
 * Interface of a group
 * 
 * */

interface GroupUser { // only used inside a group object
  id: number;
}
export interface Group {
    id: number;
    name: string;
    admin_id: number;
    users: GroupUser[];
  }