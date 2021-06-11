/* *
 * Interface of a group
 * 
 * */

interface GroupUser { // only used inside a group object
  id: string;
}
export interface Group {
    id: number;
    name: string;
    admin_id: string;
    users: GroupUser[];
  }