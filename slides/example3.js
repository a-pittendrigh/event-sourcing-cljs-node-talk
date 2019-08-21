// listener pattern
const store = { accounts: [], getCurrentState: (username) => ({}) };

const newMeterReading = (reading, username) => store.addMeterReading(reading, 'a.pittendrigh@gmail.com')
const accountCreated = (email) => { email }

const notifyOfChange = (listeners, event) => {
    console.log('==> Notifying Listeners');
    listeners.forEach((listener) => {listener.notify(event, listener)})
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

const listeners = [createListener('a.pittendrigh@gmail.com')]

console.log(listeners[0]);
// notifyOfChange(listeners, events);
notifyOfChange(listeners, { data: { username: 'a.pittendrigh@gmail.com' }});