export interface ClientData {
  createdAt?: string | null;
  updatedAt?: string | null;
  createdBy?: string | null;
  updatedBy?: string | null;
  id?: string | null;
  tenantId: string;
  clientId: string;
  clientSecret: string;
  name: string;
  deleted?: boolean | false;
}
