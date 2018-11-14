Component({
    externalClasses: ['wux-class'],
    options: {
        multipleSlots: true,
    },
    properties: {
        bordered: {
            type: Boolean,
            value: true,
        },
        full: {
            type: Boolean,
            value: false,
        },
        title: {
            type: String,
            value: '',
        },
        thumb: {
            type: String,
            value: '',
        },
        thumbStyle: {
            type: String,
            value: '',
        },
        extra: {
            type: String,
            value: '',
        },
        isLink: {
            type: Boolean,
            value: false,
        },
        openType: {
            type: String,
            value: 'navigateTo',
        },
        url: {
            type: String,
            value: '',
        },
        delta: {
            type: Number,
            value: 1,
        },
    },
    methods: {
        onTap() {
            const { url, isLink, openType, delta } = this.data
            const navigate = [
                'navigateTo',
                'redirectTo',
                'switchTab',
                'navigateBack',
                'reLaunch',
            ]

            this.triggerEvent('click')

            if (!isLink || !url) {
                return false
            } else if (!navigate.includes(openType)) {
                return console.warn('openType 属性可选值为 navigateTo、redirectTo、switchTab、navigateBack、reLaunch', openType)
            } else if (openType === 'navigateBack') {
                return wx[openType].call(wx, { delta })
            } else {
                return wx[openType].call(wx, { url })
            }
        },
    }
})