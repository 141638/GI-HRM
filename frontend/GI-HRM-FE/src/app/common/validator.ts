import {
  AbstractControl,
  FormArray,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';

export class CustomValidator {
  static alreadyExists(formGroup: FormGroup): ValidatorFn {
    return (abstractControl: AbstractControl): ValidationErrors | null => {
      return { alreadyExists: true };
    };
  }

  static checkUnregistered(formGroup: FormGroup): ValidatorFn {
    return (abstractControl: AbstractControl): ValidationErrors | null => {
      return { unregistered: true };
    };
  }

  static wrongEmail(formGroup: FormGroup): ValidatorFn {
    return (abstractControl: AbstractControl): ValidationErrors | null => {
      return { wrongEmail: true };
    };
  }

  static wrongFormat(formGroup: FormGroup): ValidatorFn {
    return (abstractControl: AbstractControl): ValidationErrors | null => {
      return { wrongFormat: true };
    };
  }

  static wrongEmailFormat(formGroup: FormGroup): ValidatorFn {
    return (abstractControl: AbstractControl): ValidationErrors | null => {
      return { wrongEmailFormat: true };
    };
  }

  static wrongEmailNotChangePassword(formGroup: FormGroup): ValidatorFn {
    return (abstractControl: AbstractControl): ValidationErrors | null => {
      return { wrongEmailNotChangePassword: true };
    };
  }

  static repPassword(formGroup: FormGroup): ValidatorFn {
    return (abstractControl: AbstractControl): ValidationErrors | null => {
      return { replacePassword: true };
    };
  }

  static sameOldPassword(formGroup: FormGroup): ValidatorFn {
    return (abstractControl: AbstractControl): ValidationErrors | null => {
      return { sameOldPassword: true };
    };
  }

  static checkIsNullEmptyUndefined(keyword: any): boolean {
    if (keyword !== null && keyword !== '' && keyword !== undefined) {
      return false;
    }
    return true;
  }
  static setValidator(formArray: FormArray, index: any, value: string) {
    formArray.controls[index].get(value)?.setValidators([Validators.required]);
    formArray.controls[index].get(value)?.updateValueAndValidity();
  }

  static setValidatorNull(formArray: FormArray, index: any, value: string) {
    formArray.controls[index].get(value)?.setValidators(null);
    formArray.controls[index].get(value)?.updateValueAndValidity();
  }

  static setEnableActive(items: FormArray, index: any, value: string) {
    items.controls[index].get(value)?.enable();
    items.controls[index].get(value)?.updateValueAndValidity();
  }

  static(
    formArray: FormArray,
    index: any,
    value: string,
    formGroup: FormGroup
  ) {
    formArray.controls[index]
      .get(value)
      ?.setValidators([CustomValidator.alreadyExists(formGroup)]);
    formArray.controls[index].get(value)?.updateValueAndValidity();
  }

  static setValidatorFormGroupMultipleValues(formGroup: FormGroup, value: string[]) {
    value.forEach(controlName => {
      this.setValidatorFormGroup(formGroup, controlName);
    });
  }

  static setValidatorFormGroup(formGroup: FormGroup, value: string) {
    formGroup.controls[value].setValidators([Validators.required]);
    formGroup.controls[value].updateValueAndValidity();
  }

  static setValidatorFormGroupAlreadyExists(
    formGroup: FormGroup,
    value: string
  ) {
    formGroup.controls[value].setValidators([
      CustomValidator.alreadyExists(formGroup),
    ]);
    formGroup.controls[value].updateValueAndValidity();
  }
  static setValidatorFormGroupCheckUnregistered(
    formGroup: FormGroup,
    value: string
  ) {
    formGroup.controls[value].setValidators([
      CustomValidator.checkUnregistered(formGroup),
    ]);
    formGroup.controls[value].updateValueAndValidity();
  }

  static setValidatorFormGroupWrongEmail(formGroup: FormGroup, value: string) {
    formGroup.controls[value].setValidators([
      CustomValidator.wrongEmail(formGroup),
    ]);
    formGroup.controls[value].updateValueAndValidity();
  }

  static setValidatorFormGroupWrongFormat(formGroup: FormGroup, value: string) {
    formGroup.controls[value].setValidators([
      CustomValidator.wrongFormat(formGroup),
    ]);
    formGroup.controls[value].updateValueAndValidity();
  }

  static setValidatorFormGroupWrongEmailFormat(
    formGroup: FormGroup,
    value: string
  ) {
    formGroup.controls[value].setValidators([
      CustomValidator.wrongEmailFormat(formGroup),
    ]);
    formGroup.controls[value].updateValueAndValidity();
  }

  static setValidatorFormGroupWrongEmailNotChange(formGroup: FormGroup, value: string) {
    formGroup.controls[value].setValidators([
      CustomValidator.wrongEmailNotChangePassword(formGroup),
    ]);
    formGroup.controls[value].updateValueAndValidity();
  }

  static setValidatorFormGroupRepPassword(formGroup: FormGroup, value: string) {
    formGroup.controls[value].setValidators([
      CustomValidator.repPassword(formGroup),
    ]);
    formGroup.controls[value].updateValueAndValidity();
  }

  static setValidatorFormGroupSameOldPassword(
    formGroup: FormGroup,
    value: string
  ) {
    formGroup.controls[value].setValidators([
      CustomValidator.sameOldPassword(formGroup),
    ]);
    formGroup.controls[value].updateValueAndValidity();
  }

  static setValidatorFormGroupNull(formGroup: FormGroup, value: string) {
    formGroup.controls[value].setValidators(null);
    formGroup.controls[value].updateValueAndValidity();
  }

  static validatorFormGroup(
    controlName: any,
    formArray: FormArray,
    index: any,
    value: string
  ) {
    if (!CustomValidator.checkIsNullEmptyUndefined(controlName)) {
      CustomValidator.setValidator(formArray, index, value);
    } else {
      CustomValidator.setValidatorNull(formArray, index, value);
    }
  }

  static setValidatorFormGroupArray(formArray: FormArray, values: any[]) {
    for (let index = 0; index < formArray.length; index++) {
      for (let j = 0; j < values.length; j++) {
        let variable = formArray.controls[index].get(values[j])?.value;
        CustomValidator.validatorFormGroup(
          variable,
          formArray,
          index,
          values[j]
        );
      }
    }
  }

  static setEnableActiveFormGroupArray(formArray: FormArray, values: any[]) {
    for (let index = 0; index < formArray.length; index++) {
      for (let j = 0; j < values.length; j++) {
        CustomValidator.setEnableActive(formArray, index, values[j]);
      }
    }
  }

  static removeItemFormGroupArray(items: FormArray) {
    while (items.length !== 0) {
      items.removeAt(0);
    }
  }
}
