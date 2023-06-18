import os

import yt_dlp

ydl_opts = {
    'format': 'm4a/bestaudio/best',
    'outtmpl': os.path.join(os.path.expanduser('~'), '%(title)s.%(ext)s'),
}


def get_video_info(url):
    with yt_dlp.YoutubeDL(ydl_opts) as ydl:
        info = ydl.extract_info(url, download=False)
        out = ydl.sanitize_info(info)
        out = {key: out[key] for key in
               out.keys() & {'title', 'thumbnail', 'view_count', 'like_count'}}
        return out


def download_audio(url):
    with yt_dlp.YoutubeDL(ydl_opts) as ydl:
        ydl.download(url)
