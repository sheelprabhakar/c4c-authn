// src/app/core/models/role.model.ts
export interface RoleData {
  createdAt?: number;
  updatedAt?: number;
  createdBy?: string;
  updatedBy?: string;
  id?: string;
  tenantId: string;
  name: string;
  deleted: boolean;
}
