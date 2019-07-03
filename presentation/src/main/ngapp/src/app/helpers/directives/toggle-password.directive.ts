import {Directive, ElementRef, HostListener, Input, OnInit} from '@angular/core';
import {MatInput} from '@angular/material';

@Directive({
    selector: '[appTogglePassword]'
})
export class TogglePasswordDirective implements OnInit {

    @Input('input') input: MatInput;

    constructor(private toggler: ElementRef) {
        toggler.nativeElement.classList.add('password-toggler');
    }

    @HostListener('click', ['$event'])
    public onClick($event): void {
        $event.stopPropagation();
        $event.preventDefault();
        this.toggleInputType();
    }

    ngOnInit(): void {
        if (this.input) {
            this.input.type = 'password';
        }
    }

    private toggleInputType(): void {
        this.input.type = this.input.type === 'password' ? 'text' : 'password';
        this.toggler.nativeElement.classList.toggle('active');
    }
}