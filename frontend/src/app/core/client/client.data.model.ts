export interface ClientData {
  createdAt: string;
  updatedAt: string | null;
  createdBy: string;
  updatedBy: string | null;
  id: string;
  tenantId: string;
  clientId: string;
  clientSecret: string;
  name: string;
  deleted: boolean;
}
