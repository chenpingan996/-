/**
 * 用户类别
 */
export type UserType = {
    id: number;
    username: string;
    userAccount: string;
    avatarUrl?: string;
    gender: number;
    phone: string;
    email: string;
    userStates: number;
    userRoles: number;
    planetCode: string;
    tags: string;
    profile?:string;
    createTime: Date;
};