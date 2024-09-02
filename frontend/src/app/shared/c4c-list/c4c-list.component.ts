import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SelectionModel } from '@angular/cdk/collections';
import { MatListModule } from '@angular/material/list';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';

export interface Item {
  id: number;
  name: string;
}

@Component({
  selector: 'app-c4c-list',
  templateUrl: './c4c-list.component.html',
  styleUrls: ['./c4c-list.component.scss'],
  standalone: true,
  imports:[CommonModule, MatListModule, MatCheckboxModule, MatButtonModule]
})
export class C4cListComponent {
  @Input() items: Item[] = [];
  @Output() selectionChange = new EventEmitter<Item[]>();

  selection = new SelectionModel<Item>(true, []);

  ngOnInit(): void {
    this.selection.changed.subscribe(() => {
      this.selectionChange.emit(this.selection.selected);
    });
  }

  isAllSelected(): boolean {
    const numSelected = this.selection.selected.length;
    const numRows = this.items.length;
    return numSelected === numRows;
  }

  masterToggle(): void {
    this.isAllSelected()
      ? this.selection.clear()
      : this.items.forEach(row => this.selection.select(row));
  }

  checkboxLabel(row?: Item): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;
  }
}
