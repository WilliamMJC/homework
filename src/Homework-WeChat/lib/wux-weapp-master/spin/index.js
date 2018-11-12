Component({
    externalClasses: ['wux-class'],
    properties: {
        customStyle: String,
        bodyCustomStyle: String,
        tip: {
            type: String,
            value: '',
        },
        size: {
            type: String,
            value: 'default',
        },
        spinning: {
            type: Boolean,
            value: false,
        },
    },
})