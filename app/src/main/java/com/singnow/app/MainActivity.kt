package com.singnow.app

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.singnow.app.firebase.FirebaseConfig
import com.singnow.app.states.VideoResponse
import com.singnow.app.states.objects.AppState
import com.singnow.app.states.viewmodels.AuthViewModel
import com.singnow.app.ui.RouterSetup
import com.singnow.app.ui.theme.SingNowTheme

@SuppressLint("StaticFieldLeak")
lateinit var navController: NavHostController

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        val video = VideoResponse(
            "https://i.ytimg.com/vi/AfM8hesghVg/mqdefault.jpg",
            "Billie Eilish - WILDFLOWER (Piano Karaoke)",
            "Billie Eilish - WILDFLOWER | Piano Karaoke Instrumental\n" +
                    "SUBSCRIBE \uD83D\uDC49 https://s2m.lnk.to/Sing2PianoYT1c \uD83C\uDFA4 Sing more Billie Eilish \uD83D\uDC49   \n" +
                    "\n" +
                    " • Billie Eilish | Piano Karaoke Instrum...  \n" +
                    "\uD83D\uDC47 Newsletter Sign Up & How To Use Our Tracks\n" +
                    "https://s2m.lnk.to/joine1c\n" +
                    "\n" +
                    "\uD83D\uDCFB \uD835\uDDE3\uD835\uDDF6\uD835\uDDEE\uD835\uDDFB\uD835\uDDFC \uD835\uDDDE\uD835\uDDEE\uD835\uDDFF\uD835\uDDEE\uD835\uDDFC\uD835\uDDF8\uD835\uDDF2 \uD835\uDDDC\uD835\uDDFB\uD835\uDE00\uD835\uDE01\uD835\uDDFF\uD835\uDE02\uD835\uDDFA\uD835\uDDF2\uD835\uDDFB\uD835\uDE01\uD835\uDDEE\uD835\uDDF9 \uD835\uDDE3\uD835\uDDF9\uD835\uDDEE\uD835\uDE06\uD835\uDDF9\uD835\uDDF6\uD835\uDE00\uD835\uDE01\uD835\uDE00\n" +
                    "Sing more Billie Eilish \uD83D\uDC49   \n" +
                    "\n" +
                    " • Billie Eilish | Piano Karaoke Instrum...  \n" +
                    "Disney:   \n" +
                    "\n" +
                    " • Disney Songs | Piano Karaoke Instrume...  \n" +
                    "Top 50 - Weekly:   \n" +
                    "\n" +
                    " • Top 50 - Weekly | Piano Karaoke Instr...  \n" +
                    "Trending Now:   \n" +
                    "\n" +
                    " • Trending Now | Piano Karaoke Instrume...  \n" +
                    "New Videos:   \n" +
                    "\n" +
                    " • New Videos | Piano Karaoke Instrumentals  \n" +
                    "Sad:   \n" +
                    "\n" +
                    " • Sad | Piano Karaoke Instrumentals  \n" +
                    "Collab Covers:   \n" +
                    "\n" +
                    " • Cover Collabs  \n" +
                    "\n" +
                    "\uD835\uDDD4\uD835\uDDFF\uD835\uDDFF\uD835\uDDEE\uD835\uDDFB\uD835\uDDF4\uD835\uDDF2\uD835\uDDFA\uD835\uDDF2\uD835\uDDFB\uD835\uDE01 \uD835\uDDE1\uD835\uDDFC\uD835\uDE01\uD835\uDDF2\uD835\uDE00:\n" +
                    "Key: F#/Gb Minor | Tempo: 74  | Time Signature: 4/4\n" +
                    "\n" +
                    "\uD835\uDDD7\uD835\uDDFC\uD835\uDE04\uD835\uDDFB\uD835\uDDF9\uD835\uDDFC\uD835\uDDEE\uD835\uDDF1 | \uD835\uDDE6\uD835\uDE01\uD835\uDDFF\uD835\uDDF2\uD835\uDDEE\uD835\uDDFA \uD83D\uDCBF\n" +
                    "Coming Soon! Current Catalogue Here:\n" +
                    "https://s2m.lnk.to/s2mLib1c\n" +
                    "\n" +
                    "\uD83D\uDCA1 \uD835\uDDDB\uD835\uDDFC\uD835\uDE04 \uD835\uDDE7\uD835\uDDFC \uD835\uDDE8\uD835\uDE00\uD835\uDDF2 \uD835\uDDE2\uD835\uDE02\uD835\uDDFF \uD835\uDDE7\uD835\uDDFF\uD835\uDDEE\uD835\uDDF0\uD835\uDDF8\uD835\uDE00\n" +
                    "\uD835\uDDD9\uD835\uDDD4\uD835\uDDE4 https://s2m.lnk.to/FAQ1c\n" +
                    "\uD835\uDDDF\uD835\uDDF6\uD835\uDDF0\uD835\uDDF2\uD835\uDDFB\uD835\uDE00\uD835\uDDF6\uD835\uDDFB\uD835\uDDF4 \uD835\uDDDC\uD835\uDDFB\uD835\uDDF3\uD835\uDDFC https://s2m.lnk.to/Lic1c\n" +
                    "\n" +
                    "\uD835\uDDE6\uD835\uDE02\uD835\uDDEF\uD835\uDDFA\uD835\uDDF6\uD835\uDE01 \uD835\uDDEE \uD835\uDDD6\uD835\uDDFC\uD835\uDE03\uD835\uDDF2\uD835\uDDFF: A chance to be featured on our Channel Homepage, Playlists & Socials \n" +
                    "1. @Sing2Piano on YT | Mention @Sing2Music on Socials | #Sing2Piano \n" +
                    "2. Or directly submit your cover to our Instagram/Facebook via the links below.\n" +
                    "┗ DM - Instagram https://s2m.lnk.to/DM-IG1c\n" +
                    "┗ MSG - Facebook https://s2m.lnk.to/FBmessenger1c\n" +
                    "  \n" +
                    "\n" +
                    " • sing2piano | Community Covers  \n" +
                    "\n" +
                    "\uD835\uDDDB\uD835\uDDFC\uD835\uDE04 \uD835\uDDE7\uD835\uDDFC \uD835\uDDE6\uD835\uDE02\uD835\uDDF4\uD835\uDDF4\uD835\uDDF2\uD835\uDE00\uD835\uDE01 \uD835\uDDE6\uD835\uDDFC\uD835\uDDFB\uD835\uDDF4 or \uD835\uDDDE\uD835\uDDF2\uD835\uDE06\n" +
                    "Your suggestions fuel what we create! \n" +
                    "1. Vote via our community page polls every New Music Friday \n" +
                    "┗ https://s2m.lnk.to/s2pComTab1c\n" +
                    "2. Write it in the Comments \uD83D\uDCAC (we read them all)\n" +
                    "3. Directly via:\n" +
                    "┗ DM - Instagram https://s2m.lnk.to/DM-IG1c\n" +
                    "┗ MSG - Facebook https://s2m.lnk.to/FBmessenger1c\n" +
                    "┗ Email - Sing2Music https://s2m.lnk.to/ContactUs1c\n" +
                    "\n" +
                    "\uD835\uDDD4\uD835\uDDEF\uD835\uDDFC\uD835\uDE02\uD835\uDE01 \uD835\uDDE6\uD835\uDDF6\uD835\uDDFB\uD835\uDDF4\uD835\uDFEE\uD835\uDDE3\uD835\uDDF6\uD835\uDDEE\uD835\uDDFB\uD835\uDDFC\n" +
                    "The #1 YouTube channel for piano karaoke instrumentals, dedicated to singers like you! Dive into our extensive collection of piano karaoke tracks for hits by Taylor Swift, Billie Eilish, BTS, and beyond. Crafted with love and precision, our accompaniments are designed to elevate your voice and transform your covers.\n" +
                    "As part of the Sing2Music brand, which encompasses Sing2Guitar and karaoke SESH, Sing2Piano is not just a channel but a companion in your musical journey.\n" +
                    "Let's make music together!\n" +
                    "\n" +
                    "\uD835\uDDD9\uD835\uDDFC\uD835\uDDF9\uD835\uDDF9\uD835\uDDFC\uD835\uDE04 \uD835\uDDE6\uD835\uDDF6\uD835\uDDFB\uD835\uDDF4\uD835\uDFEE\uD835\uDDE0\uD835\uDE02\uD835\uDE00\uD835\uDDF6\uD835\uDDF0\n" +
                    "Website https://s2m.lnk.to/Web1c\n" +
                    "Instagram https://s2m.lnk.to/IIG1c\n" +
                    "TikTok https://s2m.lnk.to/TkT1c\n" +
                    "Facebook https://s2m.lnk.to/Fbk1c\n" +
                    "Twitter https://s2m.lnk.to/Twr1c\n" +
                    "Email/Newsletter https://s2m.lnk.to/join1c\n" +
                    "Sheet Music https://s2m.lnk.to/smc1c\n" +
                    "Linkinbio https://s2m.lnk.to/linkinbio1c\n" +
                    "\n" +
                    "#Karaoke #BillieEilish #Lyrics #Instrumental\n" +
                    "\n" +
                    "\uD835\uDDE5\uD835\uDDF2\uD835\uDDF5\uD835\uDDF2\uD835\uDDEE\uD835\uDDFF\uD835\uDE00\uD835\uDDEE\uD835\uDDF9 \uD835\uDDD6\uD835\uDE02\uD835\uDDF2\uD835\uDE00 \uD83C\uDFA7\n" +
                    "00:00 Intro\n" +
                    "00:12 Verse 1\n" +
                    "00:38 Pre Chorus\n" +
                    "01:04 Chorus\n" +
                    "01:34 Interlude\n" +
                    "01:47 Verse 2\n" +
                    "02:12 Pre Chorus\n" +
                    "02:25 Chorus\n" +
                    "02:55 Bridge\n" +
                    "03:24 Chorus\n" +
                    "03:37 Bridge",
            "https://firebasestorage.googleapis.com/v0/b/singnow-f2139.appspot.com/o/videos%2FY2meta.app-Billie%20Eilish%20-%20WILDFLOWER%20(Piano%20Karaoke)-(1080p60).mp4?alt=media&token=2b1aa0a0-d2d3-4ea0-a490-8ea7e14f34cd",
            "Things fall apart\n" +
                    "And time breaks your heart\n" +
                    "I wasn't there, but I know\n" +
                    "She was your girl\n" +
                    "\n" +
                    "You showed her the world\n" +
                    "But fell out of love and you both let go\n" +
                    "She was cryin’ on my shoulder\n" +
                    "All I could do was hold her\n" +
                    "\n" +
                    "Only made us closer until July\n" +
                    "Now I know that you love me\n" +
                    "You don't need to remind me\n" +
                    "I should put it all behind me, shouldn't I?\n" +
                    "\n" +
                    "But I see her in the back of my mind\n" +
                    "All the time\n" +
                    "Like a fever, like I’m burning alive\n" +
                    "Like a sign\n" +
                    "\n" +
                    "Did I cross the line?\n" +
                    "(Mm) Hmm\n" +
                    "\n" +
                    "Well, good things don't last\n" +
                    "And life moves so fast\n" +
                    "I'd never ask who was better\n" +
                    "'Cause she couldn't be\n" +
                    "\n" +
                    "More different from me\n" +
                    "Happy and free in leather\n" +
                    "And I know that you love me\n" +
                    "You don't need to remind me\n" +
                    "Wanna put it all behind me, but baby\n" +
                    "\n" +
                    "I see her in the back of my mind\n" +
                    "All the time\n" +
                    "Feels like a fever, like I'm burning alive\n" +
                    "Like a sign\n" +
                    "\n" +
                    "Did I cross the line?\n" +
                    "\n" +
                    "You say no one knows you so well\n" +
                    "But every time you touch me\n" +
                    "I just wonder how she felt\n" +
                    "Valentine's Day, cryin’ in the hotel\n" +
                    "\n" +
                    "I know you didn’t mean to hurt me\n" +
                    "So I kept it to myself\n" +
                    "And I wonder, do you see her\n" +
                    "In the back of your mind in my eyes?\n" +
                    "\n" +
                    "You say no one knows you so well\n" +
                    "But every time you touch me\n" +
                    "\n" +
                    "I just wonder how she felt\n" +
                    "Valentine's Day, cryin’ in the hotel\n" +
                    "I know you didn't mean to hurt me\n" +
                    "So I kept it to myself"
        )
        val vidR = FirebaseConfig.videosRef()
        val key = vidR.push().key
        if (key != null) {
            vidR.child(key).setValue(video)
        }
        setContent {
            AppState.Init(this)
            navController = rememberNavController()
            SingNowTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    RouterSetup(navController)
                }
            }
        }
    }
}