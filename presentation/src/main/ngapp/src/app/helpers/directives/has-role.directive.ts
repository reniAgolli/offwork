import {Directive, ElementRef, Input, OnInit} from '@angular/core';
import {AuthServiceService} from "../../services/auth-service.service";

@Directive({
    selector: '[hasRole]'
})
export class hasRoleDirective implements OnInit{

    @Input() roles: string[];

    constructor(private element: ElementRef) {
    }

    ngOnInit(): void {
        if (this.roles && !AuthServiceService.hasAnyRole(this.roles)) {
            this.element.nativeElement.style.display = 'none';
        }
    }

}
