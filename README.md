# UIClient


WeightGuard is a Security complex for preventing theft in supermarkets. Shelves for goods are mounted on strain gauges, analog signal from which is digitized by the ADC and process on a microcontroller. The processed data transmitted to the server via Ethernet. The server sends an alarm to an android application and activates video recording from cameras, which are installed opposite each rack.

UI client application was made for viewing of data which is transfered to the server from different devices in security system. UI client allows setting up the system, view video files which was stored on the server during alarm situation.

All communication between UI client and server carried out using HTTP requests.
