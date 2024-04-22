export interface ToastRefDto {
    message: string | undefined,
    header?: string,
    severity: 'success' | 'info' | 'warn' | 'error' | 'secondary' | 'contrast' | undefined,
    life?: number
}