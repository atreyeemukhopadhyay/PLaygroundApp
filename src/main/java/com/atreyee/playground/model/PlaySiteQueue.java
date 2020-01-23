package com.atreyee.playground.model;

public class PlaySiteQueue {
    private Kid[] kidQueue;
    private int front = 0;
    private int back = 0;
    private int vipIndex = -1;

    public PlaySiteQueue(int length) {
        this.kidQueue = new Kid[length];
    }

    public boolean isEmpty(){
        if(this.front==this.back) return true;
        return false;
    }
    public int add(Kid t) {
        if ((this.back + 1) % this.kidQueue.length == this.front % this.kidQueue.length) resize();
        int kidQueuePosition = -1;
        if (t.isVip()) {
            if (this.back > this.vipIndex + 4) {
                int temp = this.back;
                while (temp > this.vipIndex + 4) {
                    Kid kid = this.kidQueue[temp % this.kidQueue.length];
                    this.kidQueue[temp + 1 % this.kidQueue.length] = kid;
                    temp--;
                }
                this.vipIndex = temp++ % this.kidQueue.length;
                this.kidQueue[this.vipIndex] = t;

            } else {
                this.vipIndex = this.back++ % this.kidQueue.length;
                this.kidQueue[this.vipIndex] = t;
            }
            kidQueuePosition = this.vipIndex;
        } else {
            this.kidQueue[this.back++ % this.kidQueue.length] = t;
            kidQueuePosition = this.back;
        }

        return kidQueuePosition;
    }

    public Kid remove(int depth) {
        int removeIndex = depth;
        Kid removedKid = this.kidQueue[depth % this.kidQueue.length];

        while (removeIndex <= this.back) {
            this.kidQueue[removeIndex % this.kidQueue.length] = this.kidQueue[removeIndex + 1 % this.kidQueue.length];
            removeIndex++;
        }
        return removedKid;
    }

    public int isAvailable(Kid kid) {
        int count = -1;
        for (Kid elem : this.kidQueue) {
            count++;
            if (elem.getTicketNumber() == kid.getTicketNumber()) {
                return count;
            }

        }
        return count;
    }

    public Kid remove() {
        return this.kidQueue[this.front++ % this.kidQueue.length];
    }

    public int size() {
        if (this.back >= this.front) return (this.back - this.front);
        return (this.kidQueue.length - this.front + this.back);
    }

    public void resize() {
        PlaySiteQueue q2 = new PlaySiteQueue(this.kidQueue.length * 10);
        while (this.size() > 0) {
            q2.add(this.remove());
        }
        this.kidQueue = q2.kidQueue;
        this.front = q2.front;
        this.back = q2.back;
        this.vipIndex = q2.vipIndex;
    }
}
