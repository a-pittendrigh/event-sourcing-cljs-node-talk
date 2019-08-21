// listener pattern

const notifyOfChange = (listeners, event) => {
    console.log('==> Notifying Listeners');
    listeners.forEach((listener) => {listener.notify(event, listener)})
}

const notify = (data) => (event) => {
    const listener = { data: data }
    notifiedOfChange(event, listener);
}

const createListener = (username) => {
    const data = { username };
    const notifier = notify(data);
    return {
        notify: notifier
    }
}
const listeners = [createListener('a.pittendrigh@gmail.com')];

const accountCreated = "account_created";
const createStore = (accounts = {}) => {
    const accountStore = accounts;
    const eventProcessors = {};
    eventProcessors[accountCreated] = (event, listeners) => {
        const username = event.data.username;
        const newAccount = { username, readings: [], events: [event] };
        accountStore[username] = newAccount;

        notifyOfChange(listeners, event);
    }
    return {
        getCurrentState: (username) => (accountStore[username]),
        processEvent: (event, listeners) => {
            eventProcessors[event.data.type](event, listeners);
        }
    }
}

const notifiedOfChange = (event, listener) => {
    console.log('=== Notification recieved! ====');

    const affectedUser = event.data.username;
    const myUser = listener.data.username;
    if (affectedUser === myUser) {
        console.log('** Something I care about changed! **', store.getCurrentState(myUser));
    }

    console.log('=== Notification processed! ====');
}

//console.log(listeners[0]);
// notifyOfChange(listeners, events);
//notifyOfChange(listeners, { data: { username: 'a.pittendrigh@gmail.com' }});

const store = createStore();
const createEvent = (type, username) => ({ data: { username, type }});
store.processEvent(createEvent(accountCreated, 'a.pittendrigh@gmail.com'), listeners);