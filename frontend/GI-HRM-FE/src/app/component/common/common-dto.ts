export class ConfirmAction {
  action: number | undefined;
  header?: string;
  message?: string;
  icon?: string;
}

export class ToastBody {
  action: number | undefined;
  header: string | undefined;
  message: string | undefined;
  icon?: string;
}
export class ColTemplate {
  colWidth: string | undefined;
  header: string | undefined;
  field?: string;
}