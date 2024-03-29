import { Component } from '@angular/core';
import {
  NzTableComponent,
  NzTheadComponent,
  NzTbodyComponent,
  NzThAddOnComponent, NzThSelectionComponent, NzThMeasureDirective, NzCellAlignDirective
} from "ng-zorro-antd/table";
import {NgForOf} from "@angular/common";
import {NzDividerComponent} from "ng-zorro-antd/divider";
import { NzImageModule } from 'ng-zorro-antd/image';
import {NzFlexDirective} from "ng-zorro-antd/flex";
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzModalComponent, NzModalContentDirective, NzModalModule, NzModalService} from "ng-zorro-antd/modal";
import {
  FormControl,
  FormGroup,
  FormsModule,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators
} from "@angular/forms";
import {NzFormControlComponent, NzFormDirective, NzFormItemComponent, NzFormLabelComponent} from "ng-zorro-antd/form";
import {NzInputDirective, NzInputGroupComponent} from "ng-zorro-antd/input";
import {NzColDirective} from "ng-zorro-antd/grid";
import {NzCheckboxComponent} from "ng-zorro-antd/checkbox";
import {NzOptionComponent, NzSelectComponent} from "ng-zorro-antd/select";

interface DataItem {
  name: string;
  numberOfSong: number;
  createAt: string;
}

@Component({
  selector: 'app-manage-account',
  standalone: true,
  imports: [
    NzModalModule,
    NzTableComponent,
    NzThAddOnComponent,
    NgForOf,
    NzTheadComponent, NzTbodyComponent, NzDividerComponent, NzThSelectionComponent, NzImageModule, NzThMeasureDirective, NzThMeasureDirective, NzCellAlignDirective, NzFlexDirective, NzButtonComponent, NzModalComponent, NzModalContentDirective, ReactiveFormsModule, NzFormItemComponent, NzFormControlComponent, NzInputGroupComponent, NzColDirective, NzFormDirective, NzInputDirective, NzFormLabelComponent, NzCheckboxComponent, NzSelectComponent, NzOptionComponent, FormsModule
  ],
  templateUrl: './manage-account.component.html',
  styleUrl: './manage-account.component.css'
})
export class ManageAccountComponent {
  constructor(private fb: NonNullableFormBuilder) {
  }

  listOfData: DataItem[] = [
    {
      name: 'John Brown',
      numberOfSong: 60,
      createAt:
        new Intl.DateTimeFormat('en-GB', {
          day: '2-digit',
          month: '2-digit',
          year: '2-digit',
        }).format(new Date('2024-12-02'))

    },
    {
      name: 'Jim Green',
      numberOfSong: 66,
      createAt:  new Intl.DateTimeFormat('en-GB', {
        day: '2-digit',
        month: '2-digit',
        year: '2-digit',
      }).format(new Date('2024-12-02'))

    },
    {
      name: 'Joe Black',
      numberOfSong: 90,
      createAt:  new Intl.DateTimeFormat('en-GB', {
        day: '2-digit',
        month: '2-digit',
        year: '2-digit',
      }).format(new Date('2024-12-02'))

    },
    {
      name: 'Jim Red',
      numberOfSong: 99,
      createAt:  new Intl.DateTimeFormat('en-GB', {
        day: '2-digit',
        month: '2-digit',
        year: '2-digit',
      }).format(new Date('2024-12-02'))

    }
  ];

  isVisible = false;

  showModal(): void {
    this.isVisible = true;
  }

  handleOk(): void {
    this.isOkLoading = true;
    setTimeout(() => {
      this.isVisible = false;
      this.isOkLoading = false;
    }, 3000);
  }

  handleCancel(): void {
    this.isVisible = false;
  }

  isVisibleCreate = false;
  isOkLoading = false;

  showModalCreate(): void {
    this.isVisibleCreate = true;
  }

  handleOkCreate(): void {
    this.isOkLoading = true;
    setTimeout(() => {
      this.isVisibleCreate = false;
      this.isOkLoading = false;
    }, 3000);
  }

  handleCancelCreate(): void {
    this.isVisibleCreate = false;
  }

  validateForm: FormGroup<{
    email: FormControl<string>
    name: FormControl<string>;
    nickname: FormControl<string>;
    required: FormControl<boolean>;
  }> = this.fb.group({
    email: [''],
    name: ['', [Validators.required]],
    nickname: [''],
    required: [false]
  });

  submitForm(): void {
    if (this.validateForm.valid) {
      console.log('submit', this.validateForm.value);
    } else {
      Object.values(this.validateForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }

  requiredChange(required: boolean): void {
    if (!required) {
      this.validateForm.controls.nickname.clearValidators();
      this.validateForm.controls.nickname.markAsPristine();
    } else {
      this.validateForm.controls.nickname.setValidators(Validators.required);
      this.validateForm.controls.nickname.markAsDirty();
    }
    this.validateForm.controls.nickname.updateValueAndValidity();
  }

  listOfOption = ['Apples', 'Nails', 'Bananas', 'Helicopters'];
  listOfSelectedValue: string[] = [];

  isNotSelected(value: string): boolean {
    return this.listOfSelectedValue.indexOf(value) === -1;
  }
}

