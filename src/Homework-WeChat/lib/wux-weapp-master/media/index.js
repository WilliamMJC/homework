Component({
    externalClasses: ['wux-class'],
    properties: {
        thumb: {
            type: String,
            value: '',
        },
        thumbStyle: {
            type: String,
            value: '',
        },
        title: {
            type: String,
            value: '',
        },
        label: {
            type: String,
            value: '',
        },
        align: {
            type: String,
            value: 'center',
        },
        userAvatar: {
            type: Boolean,
            value: '',
        },
        userNickName: {
            type: Boolean,
            value: ''
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