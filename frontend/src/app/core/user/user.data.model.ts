import { PolicyData } from "../policy/policy.data.model";

export interface UserData {
  createdAt: Date | null;
  updatedAt: Date | null;
  createdBy: string;
  updatedBy: string;
  id: string;
  tenantId: string;
  firstName: string;
  middleName: string;
  lastName: string;
  mobile: string;
  email: string;
  passwordHash: string;
  lastLogin: Date | null;
  intro: string | null;
  profile: string | null;
  userName: string;
  locked: boolean;
  deleted: boolean;
}

export interface UserDetailsData {
  userInfo: UserData;
  policies: PolicyData[];
}
