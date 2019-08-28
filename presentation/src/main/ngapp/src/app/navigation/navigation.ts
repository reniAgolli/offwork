import {FuseNavigation} from '@fuse/types';

export const navigation: FuseNavigation[] = [
    {
        id: 'leaves',
        title: 'Leaves',
        type: 'item',
        icon: 'perm_contact_calendar',
        url: 'leaves',
        role: ['ROLE_USER',"ROLE_ADMIN"]

    },
    {
        id: 'profile',
        title: 'Profile',
        type: 'item',
        icon: 'account_circle',
        url: 'user-details',
        role: ['ROLE_USER',"ROLE_ADMIN"]
    },
    {
        id: 'users',
        title: 'Users',
        type: 'item',
        icon: 'supervised_user_circle',
        url: 'admin/users',
        role: ["ROLE_ADMIN"]
    },
];
